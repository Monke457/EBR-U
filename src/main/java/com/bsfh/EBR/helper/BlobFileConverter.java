package com.bsfh.EBR.helper;

import com.bsfh.EBR.model.BlobFile;
import com.bsfh.EBR.service.DBService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;

@Component
public class BlobFileConverter implements Converter<MultipartFile, BlobFile> {
    private final DBService<BlobFile> service;

    public BlobFileConverter(DBService<BlobFile> service) {
        this.service = service;
    }

    @Override
    public BlobFile convert(MultipartFile file) {
        if(!file.isEmpty()) {
            try {
                BlobFile bf = new BlobFile(file.getOriginalFilename(), file.getContentType(), new SerialBlob(file.getBytes()), null);
                service.create(bf);
                return bf;

            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
