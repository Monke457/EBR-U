package com.bsfh.EBR.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

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

    public BlobFile(String fileName, String mime, Blob fileData) {
        this.fileName = fileName;
        this.mime = mime;
        this.fileData = fileData;
    }

    @Override
    public String toString() {
        return fileName;
    }
}