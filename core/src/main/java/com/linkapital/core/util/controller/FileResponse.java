package com.linkapital.core.util.controller;

import org.springframework.http.MediaType;

import java.io.InputStream;

public class FileResponse {

    private InputStream file;
    private MediaType mediaType;

    public FileResponse() {
    }

    public FileResponse(InputStream file, MediaType mediaType) {
        this.file = file;
        this.mediaType = mediaType;
    }

    public InputStream getFile() {
        return this.file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public MediaType getMediaType() {
        return this.mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

}
