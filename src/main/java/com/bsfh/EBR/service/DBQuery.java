package com.bsfh.EBR.service;

public class DBQuery {
    private String search;
    private int limit;
    private boolean exact = true;

    public DBQuery() {
    }

    public DBQuery(String search) {
        this.search = search;
    }

    public DBQuery(int limit) {
        this.limit = limit;
    }

    public DBQuery(String search, boolean exact) {
        this.search = search;
        this.exact = exact;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isExact() {
        return exact;
    }

    public void setExact(boolean exact) {
        this.exact = exact;
    }
}
