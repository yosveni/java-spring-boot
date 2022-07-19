package com.linkapital.core.util.decompress;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.directory.impl.MockMultipartFile;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.util.MimeTypes.getMimeType;
import static java.io.File.createTempFile;
import static java.lang.Runtime.getRuntime;
import static java.nio.file.Files.createTempDirectory;
import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.lang3.SystemUtils.IS_OS_LINUX;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.apache.tika.mime.MimeTypes.PLAIN_TEXT;
import static org.apache.tika.mime.MimeTypes.XML;
import static org.springframework.util.StringUtils.hasText;

/**
 * Default static for {@link DecompressUtil}
 * Class with functionality to decompress files in formats: "zip", "jar", "gz" and "rar"
 *
 * @since 0.0.1
 */
public class DecompressUtil {

    private static final Logger log = LoggerFactory.getLogger(DecompressUtil.class);

    private DecompressUtil() {
    }

    /**
     * Given a compressed file, it returns the list of all files contained in the root directory and its subdirectories
     *
     * @param multipartFile {@link MultipartFile} the compressed file
     * @return array of {@link List}<{@link MultipartFile}> the files contained in the root directory and its subdirectories
     * @throws UnprocessableEntityException if the file format is not allowed or if there is an error when unzipping
     */
    public static List<MultipartFile> decompress(MultipartFile multipartFile) throws UnprocessableEntityException {
        if (multipartFile == null)
            return Collections.emptyList();

        var ext = getExtension(multipartFile.getOriginalFilename());
        if (!hasText(ext))
            return Collections.emptyList();

        return buildMockMultipartFile(unRar(multipartFile, "." + ext));
    }
    //endregion

    //region Functionality in charge of decompressing files
    private static List<File> unRar(MultipartFile file, String ext) throws UnprocessableEntityException {
        var tempFile = multipartToFile(file, ext);
        File tempDir;
        List<File> files = null;

        try {
            tempDir = createTempDirectory("linkapital").toFile();
            var cmd = "";
            if (IS_OS_WINDOWS)
                cmd = "winrar e -r -y -o+ -ibck ";
            else if (IS_OS_LINUX)
                cmd = "rar" + " X -o+ ";
            //else
            // another operative sistem command to execute rar

            var process = getRuntime().exec(cmd + tempFile.getAbsolutePath() + " " + tempDir.getAbsolutePath());
            var br = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));

            br.close();
            process.waitFor();
            process.destroy();

            files = stream(requireNonNull(tempDir.listFiles()))
                    .filter(f -> getMimeType(f).equals(XML))
                    .toList();

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new UnprocessableEntityException(msg("decompress.file.decompressing.error"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        deleteQuietly(tempFile);

        if (files == null || files.isEmpty())
            throw new UnprocessableEntityException(msg("decompress.file.decompressing.error"));

        return files;
    }
    //endregion

    //region Functionality in charge of converting a list of File to Multipart
    private static List<MultipartFile> buildMockMultipartFile(List<File> files) throws UnprocessableEntityException {
        if (files.isEmpty())
            return Collections.emptyList();

        var array = new ArrayList<MultipartFile>();

        for (File file : files)
            try (var input = new FileInputStream(file)) {
                array.add(new MockMultipartFile("file", file.getName(), PLAIN_TEXT,
                        IOUtils.toByteArray(input)));
            } catch (IOException e) {
                throw new UnprocessableEntityException(e.getMessage());
            }

        deleteQuietly(files.get(0).getParentFile());

        return array;
    }
    //endregion

    //region Functionality in charge of converting a Multipart to File
    private static File multipartToFile(MultipartFile multipart, String ext) throws UnprocessableEntityException {
        var tempFile = newTempFile(ext);

        try {
            multipart.transferTo(tempFile);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new UnprocessableEntityException(msg("decompress.file.decompressing.error"));
        }

        return tempFile;
    }

    //region Functionality in charge of creating a temporary file
    private static File newTempFile(String ext) throws UnprocessableEntityException {
        try {
            return createTempFile("file1", ext);
        } catch (IOException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }
    //endregion

}
