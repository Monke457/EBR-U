package com.bsfh.EBR.helper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Component
public class BlobConverter implements Converter<MultipartFile, Blob> {

    @Override
    public Blob convert(MultipartFile file) {
        if(!file.isEmpty()) {
            try {
                Blob blob = new SerialBlob(file.getBytes());
                return blob;
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
