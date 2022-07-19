package com.linkapital.core.services.storage.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.linkapital.core.exceptions.StorageException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.storage.StorageService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.Map;

import static com.amazonaws.regions.Regions.SA_EAST_1;
import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.StringUtils.hasText;

@Service
public class StorageServiceImpl implements StorageService {

    private final AmazonS3 s3client;
    private final String bucket;
    private final String bucketNeoWay;
    private final String dataSource;
    Logger log = getLogger(StorageServiceImpl.class);

    public StorageServiceImpl(
            @Value("${s3.bucket}") String bucket,
            @Value("${s3.bucket-neo-way}") String bucketNeoWay,
            @Value("${data-source-storage}") String dataSource) {
        this.dataSource = dataSource;
        this.s3client = this.getClient();
        this.bucket = bucket;
        this.bucketNeoWay = bucketNeoWay;
    }

    @Override
    public void store(byte[] bytes, String url) throws StorageException {
        var byteArrayInputStream = new ByteArrayInputStream(bytes);
        var objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(bytes.length);
        try {
            s3client.putObject(bucket, url, byteArrayInputStream, objectMetadata);
        } catch (SdkClientException e) {
            log.error(e.getLocalizedMessage());
            throw new StorageException(msg("storage.error.store.file"));
        }
    }

    @Override
    public void storeJsonNeoWay(String id, @NotNull Map map) {
        var byteArrayOutputStream = new ByteArrayOutputStream();

        try (var outputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            outputStream.writeObject(map);
            storeNeoWay(byteArrayOutputStream.toByteArray(), String.format("%s.json", id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void storeJucesp(File file, String url) {
        s3client.putObject(bucket, url, file);
    }

    @Override
    public byte[] getFile(String url) throws StorageException {
        try {
            return s3client.getObject(bucket, url).getObjectContent().readAllBytes();
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public byte[] getFileNeoWay(String url) throws StorageException {
        try {
            var s3Object = s3client.getObject(bucketNeoWay, url);
            var buffer = s3Object.getObjectContent().readAllBytes();
            s3Object.close();

            return buffer;
        } catch (Exception e) {
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> getFileToMap(String url) throws StorageException {
        var buffer = getFileNeoWay(url);
        try {
            return (Map) new ObjectInputStream(new ByteArrayInputStream(buffer)).readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public void deleteFile(String url) {
        s3client.deleteObject(bucket, url);
    }

    @Override
    public String getExtension(MultipartFile file) throws StorageException {
        if (file.isEmpty())
            throw new StorageException(msg("storage.error.store.empty.file", file.getName()));

        var originalFileName = file.getOriginalFilename();
        return hasText(originalFileName)
                ? originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
                : "pdf";
    }

    @Override
    public String getExtension(byte[] bytes) {
        var pdf = "pdf";
        String mimeType = null;
        try {
            mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mimeType == null)
            return pdf;

        var tokens = mimeType.split("[/]");
        var fileExtension = tokens[1];

        return hasText(fileExtension)
                ? fileExtension
                : "pdf";
    }

    @Override
    public File getFile(InputStream inputStream) throws UnprocessableEntityException {
        File file;

        try {
            file = File.createTempFile("file", null);
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getLocalizedMessage());
        }

        try (OutputStream outStream = new FileOutputStream(file)) {
            int read;
            var buffer = new byte[inputStream.available()];

            while ((read = inputStream.read(buffer)) != -1)
                outStream.write(buffer, 0, read);
        } catch (IOException e) {
            throw new UnprocessableEntityException(e.getLocalizedMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.getLocalizedMessage();
            }
        }

        return file;
    }

    private void storeNeoWay(byte[] bytes, String url) {
        var byteArrayInputStream = new ByteArrayInputStream(bytes);
        var objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(bytes.length);
        s3client.putObject(bucketNeoWay, url, byteArrayInputStream, objectMetadata);
    }

    private AmazonS3 getClient() {
        return dataSource.equals("MOCK")
                ? AmazonS3ClientBuilder
                .standard()
                .withRegion(SA_EAST_1)
                .build()
                : AmazonS3ClientBuilder
                        .standard()
                        .withCredentials(new InstanceProfileCredentialsProvider(false))
                        .withRegion(SA_EAST_1)
                        .build();
    }

}
