package com.alvaro.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.model.file.UploadedFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;

@Slf4j
@Getter
public abstract class UploaderBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String path;
    @Setter
    private transient UploadedFile uploadedFile;

    protected UploaderBean(final String path){
        this.path = path;
    }

    /**
     * This method returns the relative path of the uploaded file that has been uploaded to the server.
     * @return the relative path of the uploaded file
     */
    public String writeFileAndGetPath(final String fileName){
        final String finalPath = this.path + fileName;
        final File f = new File(finalPath);
        try(final FileOutputStream fos = new FileOutputStream(f)){
            Files.createDirectories(f.toPath().getParent());
            fos.write(this.uploadedFile.getContent());
            fos.flush();
            return finalPath;
        }catch (final IOException e){
            log.error("Error uploading file", e);
            return "";
        }
    }

}
