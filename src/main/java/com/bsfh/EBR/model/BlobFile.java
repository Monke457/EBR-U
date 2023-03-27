package com.bsfh.EBR.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BlobFile implements DBEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String fileName;
    private String mime;
    @Lob
    private Blob fileData;
    @OneToOne
    private Book book;

    public BlobFile(String fileName, String mime, Blob fileData, Book book) {
        this.fileName = fileName;
        this.mime = mime;
        this.fileData = fileData;
        this.book = book;
    }

    @Override
    public String toString() {
        return fileName;
    }
}