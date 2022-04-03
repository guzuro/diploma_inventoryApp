package com.guzuro.handlers;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class UploadFileHandler {

    public static void uploadFile(RoutingContext context) {
        HttpServerResponse response = context.response();

        Set<FileUpload> uploads = context.fileUploads();

        ArrayList<String> photos = new ArrayList<>();

        uploads.forEach(upload -> {
            try {
                File uploadedFile = new File(upload.uploadedFileName());
                uploadedFile.renameTo(new File("webroot/" + upload.fileName()));
                uploadedFile.createNewFile();
                photos.add(upload.fileName());
            } catch (IOException e) {
                e.printStackTrace();
                response.setStatusCode(500)
                        .putHeader("content-type", "application/json; charset=UTF-8")
                        .setStatusMessage(e.getMessage())
                        .end();
            }
        });

        JsonArray uploadsJson = new JsonArray();

        photos.forEach(photo -> uploadsJson.add(photo));

        response
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=UTF-8")
                .end(uploadsJson.encodePrettily());
    }
}
