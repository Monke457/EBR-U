package com.bsfh.EBR.service;

import com.bsfh.EBR.model.DBEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public class DBService<T extends DBEntity> {

    @PersistenceContext
    protected EntityManager em;

    @Transactional
    public Stream<T> find(Class<T> type, DBQuery query) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> root = cq.from(type);

        if(query.getSearch() != null) {
            cq.where(cb.or(createLikePredicates(cb, root, query.getSearch(), query.isExact()))).distinct(true);
        }

        if(query.getLimit() != 0) {
            return em.createQuery(cq).setMaxResults(query.getLimit()).getResultStream();
        }
        return em.createQuery(cq).getResultStream();
    }

    @Transactional
    public Stream<T> findAll(Class<T> type, String sortField)  {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> root = cq.from(type);

        if(sortField != null) {
            cq.orderBy(cb.asc(root.get(sortField)));
        }

        return em.createQuery(cq).getResultStream();
    }

    @Transactional
    public Stream<T> findAll(Class<T> type)  {
        return findAll(type, null);
    }

    @Transactional
    public T find(Class<T> type, UUID id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> root = cq.from(type);

        cq.where(cb.equal(root.get("id"), id));

        if (!em.createQuery(cq).getResultList().isEmpty()) {
            return em.createQuery(cq).getSingleResult();
        }
        return null;
    }

    @Transactional
    public T findByUniqueAttribute(Class<T> type, String field, String value) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> root = cq.from(type);

        cq.where(cb.equal(root.get(field), value));

        if (!em.createQuery(cq).getResultList().isEmpty()) {
            return em.createQuery(cq).getSingleResult();
        }
        return null;
    }

    public Stream<T> findByRelation(Class<T> type, String field, UUID relationId, int limit, String sortField) {
        return findByRelation(type, field, Set.of(relationId), limit, sortField);
    }

    @Transactional
    public Stream<T> findByRelation(Class<T> type, String field, Set<UUID> relationIds, int limit, String sortField) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> root = cq.from(type);
        Join<T, ?> join = root.join(field, JoinType.LEFT);

        cq.where(join.get("id").in(relationIds)).distinct(true);

        if(sortField != null) {
            cq.orderBy(cb.asc(root.get(sortField)));
        }

        if(limit != 0) {
            return em.createQuery(cq).setMaxResults(limit).getResultStream();
        }
        return em.createQuery(cq).getResultStream();
    }

    private Predicate[] createLikePredicates(CriteriaBuilder cb, Root<T> root, String search, boolean exact) {
        Set<Predicate> predicates = new HashSet<>();

        String[] terms = exact ? new String[]{search} : search.split(" ");

        for(String term : terms) {
            Arrays.stream(root.getJavaType().getDeclaredFields())
                    .filter(field -> field.getType().equals(String.class) || field.getType().isPrimitive() || field.getType().equals(LocalDate.class))
                    .forEach(field -> predicates.add(cb.like(root.get(field.getName()).as(String.class), "%" + term + "%")));
        }

        return predicates.toArray(new Predicate[]{});
    }

    public boolean exists(Class<T> type, UUID id) {
        return find(type, id) != null;
    }

    @Transactional
    public void create(T item) {
        em.persist(item);
    }

    @Transactional
    public void createAll(T[] items) {
        for(T item : items) {
            em.persist(item);
        }
    }

    @Transactional
    public void update(T item) {
        em.merge(item);
    }

    @Transactional
    public void delete(Class<T> type, UUID id) {
        T item = find(type, id);
        em.remove(em.merge(item));
    }
}