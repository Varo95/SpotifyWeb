package com.alvaro.utils;

import lombok.extern.slf4j.Slf4j;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.web.context.annotation.SessionScope;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

@SessionScope
@Slf4j
public class Tools {

    private Tools(){
    }

    /**
     * This method returns a StreamedContent object from a file path
     * for PrimeFaces components
     * @param path the path of the file
     * @return a StreamedContent object
     */

    public static StreamedContent getStreamedContent(final String path) {
        final String contentType = "image/"+path.substring(path.lastIndexOf(".")+1);
        final String name = path.substring(path.lastIndexOf("/")+1);
        return DefaultStreamedContent.builder().stream(()->{
            try (final FileInputStream fis = new FileInputStream(path)){
                return new ByteArrayInputStream(fis.readAllBytes());
            } catch (final IOException e) {
                log.error("Error getting file", e);
                return null;
            }
        }).contentType(contentType).name(name).build();
    }
}
