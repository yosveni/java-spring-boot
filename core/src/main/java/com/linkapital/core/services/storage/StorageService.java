package com.linkapital.core.services.storage;

import com.linkapital.core.exceptions.StorageException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * Default interface for {@link StorageService} service,
 * with the responsibility of performing operations on the application's file storage\
 *
 * @since 0.0.1
 */
public interface StorageService {

    /**
     * Get the extension of the given file
     *
     * @param file {@link MultipartFile} the file to retrieve the extension
     * @return {@link String}
     * @throws StorageException if any error occurs
     */
    String getExtension(MultipartFile file) throws StorageException;

    /**
     * Get the extension of the given file
     *
     * @param bytes {@link Byte}[] the byte array of the file to retrieve the extension
     * @return {@link String}
     */
    String getExtension(byte[] bytes);

    /**
     * Convert an {@link InputStream} in a {@link File} object
     *
     * @param inputStream {@link InputStream} the object to be transform in a file
     * @return {@link File}
     * @throws UnprocessableEntityException if any error happen converting the stream file received in a file object
     */
    File getFile(InputStream inputStream) throws UnprocessableEntityException;

    /**
     * Get a file form a request url
     *
     * @param url {@link String} an url that reference a file in the file storage
     * @return {@link Byte}[]
     * @throws StorageException if any error occurs
     */
    byte[] getFile(String url) throws StorageException;

    /**
     * Retrieve a file from the NeoWay bucket
     *
     * @param url {@link String} an url that reference a file in the NeoWay file storage
     * @return {@link Byte}[]
     * @throws StorageException if any error occurs
     */
    byte[] getFileNeoWay(String url) throws StorageException;

    /**
     * Retrieve a file from the NeoWay bucket
     *
     * @param url {@link String} an url that reference a file in the NeoWay file storage
     * @return {@link Map}<{@link String}, {@link Object}>
     * @throws StorageException if any error occurs
     */
    Map<String, Object> getFileToMap(String url) throws StorageException;

    /**
     * Store the given file and send to the given url
     *
     * @param bytes {@link Byte}[] the byte array of the file to be upload to the file storage
     * @param url   {@link String} the url to send the file
     * @throws StorageException if any error occurs
     */
    void store(byte[] bytes, String url) throws StorageException;

    /**
     * Store json from NeoWay API
     *
     * @param id  {@link String} the id of the json to be send to NeoWay API
     * @param map {@link Map} the data to be send to NeoWay API
     */
    void storeJsonNeoWay(String id, @NotNull Map map);

    /**
     * Storage a file in the Jucesp bucket
     *
     * @param file {@link File} a file to be send to the Jucesp bucket
     * @param url  {@link String} the url to send the given file
     */
    void storeJucesp(File file, String url);

    /**
     * Delete a file from the given url
     *
     * @param url {@link String} the file url to be deleted
     */
    void deleteFile(String url);

}
