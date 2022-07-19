package com.linkapital.identification.services.impl;

import com.linkapital.identification.exceptions.EncoderIdentificationException;
import com.linkapital.identification.exceptions.FailedIdentificationException;
import com.linkapital.identification.exceptions.UnauthorizedIdentificationException;
import com.linkapital.identification.services.IIdentificationService;
import com.linkapital.identification.services.domains.Token;
import com.linkapital.identification.services.domains.content.ContentExtractionInfo;
import com.linkapital.identification.services.domains.content.RequestContentExtraction;
import com.linkapital.identification.services.domains.face.FaceCompareInfo;
import com.linkapital.identification.services.domains.face.RequestFaceCompare;
import com.linkapital.identification.services.domains.liveness.LivenessDetectionInfo;
import com.linkapital.identification.services.domains.liveness.RequestLivenessDetection;
import lombok.NonNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import static com.linkapital.identification.services.impl.ResponseError.processException;
import static java.lang.Runtime.getRuntime;
import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.deleteIfExists;
import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.FileUtils.getFile;
import static org.apache.commons.lang3.SystemUtils.IS_OS_LINUX;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.util.StringUtils.hasText;

@Service
public class IIdentificationServiceImpl implements IIdentificationService {

    private static final String TOKEN_AUTH = "59d37db6d4e8442ae2169dd2180777ce";
    private static final String URL_AUTH = "https://mostqiapi.com/user/authenticate";
    private static final String URL_CONTEXT_EXTRACTION = "https://mostqiapi.com/process-image/content-extraction";
    private static final String URL_FACE_COMPARE = "https://mostqiapi.com/process-image/biometrics/face-compare";
    private static final String URL_LIVENESS_DETECTION = "https://mostqiapi.com/liveness/detect";
    private final RestTemplate restTemplate;

    public IIdentificationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ContentExtractionInfo contextExtraction(MultipartFile file,
                                                   String type)
            throws FailedIdentificationException, UnauthorizedIdentificationException {

        var header = getAuthentication();
        var binaries = getBytes(file);
        var body = new RequestContentExtraction()
                .withFileBase64(Base64.getEncoder().encodeToString(binaries))
                .withReturnImage(true)
                .withType(type);

        try {
            return restTemplate
                    .postForEntity(URL_CONTEXT_EXTRACTION,
                            new HttpEntity<>(body, header),
                            ContentExtractionInfo.class)
                    .getBody();
        } catch (HttpClientErrorException e) {
            throw processException(e);
        }
    }

    @Override
    public LivenessDetectionInfo livenessDetection(MultipartFile file)
            throws FailedIdentificationException, UnauthorizedIdentificationException, EncoderIdentificationException {

        var header = getAuthentication();
        var encodedFile = encodeFile(file);
        if (encodedFile == null || encodedFile.length == 0)
            return null;

        var body = new RequestLivenessDetection()
                .withFile(Base64.getEncoder().encodeToString(encodedFile));
        try {
            return restTemplate
                    .postForEntity(URL_LIVENESS_DETECTION,
                            new HttpEntity<>(body, header),
                            LivenessDetectionInfo.class)
                    .getBody();
        } catch (HttpClientErrorException e) {
            throw processException(e);
        }
    }

    @Override
    public FaceCompareInfo faceCompare(byte[] base64Document,
                                       byte[] base64Image)
            throws FailedIdentificationException, UnauthorizedIdentificationException {

        var header = getAuthentication();
        var requestFaceCompare = new RequestFaceCompare()
                .withFaceFileBase64A(base64Document)
                .withFaceFileBase64B(base64Image);

        try {
            return restTemplate
                    .postForEntity(URL_FACE_COMPARE,
                            new HttpEntity<>(requestFaceCompare, header),
                            FaceCompareInfo.class)
                    .getBody();
        } catch (HttpClientErrorException e) {
            throw processException(e);
        }
    }

    //region Get Authentication from API
    private @NonNull HttpHeaders getAuthentication() throws UnauthorizedIdentificationException {
        var response = restTemplate
                .postForEntity(URL_AUTH,
                        new Token().withToken(TOKEN_AUTH),
                        Token.class);

        var body = response.getBody();
        if (body == null || !hasText(body.getToken()))
            throw new UnauthorizedIdentificationException("Authentication error");

        var header = new HttpHeaders();
        header.setContentType(APPLICATION_JSON);
        header.add("Authorization", "Bearer " + body.getToken());

        return header;
    }
    //endregion

    //region Encode file video
    private byte[] encodeFile(@NonNull MultipartFile multipartFile) throws EncoderIdentificationException {
        /*todo poner el path en linux*/
        var pathLivenessEncoder = IS_OS_WINDOWS
                ? "C:/MOST_liveness_encoder"
                : "/app/MOST_liveness_encoder";
        var pathConfig = pathLivenessEncoder + "/samples/livenc_enc.cfg";

        try {
            var tempFile = createTempFile(null, null).toFile();
            var tempFileEncoded = createTempFile("output_name", ".enc");
            multipartFile.transferTo(tempFile);

            var cmd = "dotnet run" +
                    " --configuration" + (IS_OS_LINUX ? "=Release" : " Release") +
                    " --video " + tempFile.getAbsolutePath() +
                    " --config " + pathConfig +
                    " --output " + tempFileEncoded;
            var process = getRuntime().exec(cmd, null, getFile(pathLivenessEncoder));
            process.waitFor();

            byte[] bytes = readFile(tempFileEncoded.toFile());
            deleteQuietly(tempFile);
            deleteIfExists(tempFileEncoded);

            return bytes;
        } catch (IOException e) {
            throw new EncoderIdentificationException(e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new EncoderIdentificationException(e.getMessage());
        }
    }
    //endregion

    //region Get the byte array of file
    private byte @NonNull [] getBytes(@NonNull MultipartFile file) throws FailedIdentificationException {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new FailedIdentificationException(e.getMessage());
        }
    }
    //endregion

    //region Read the encoded file
    private byte[] readFile(File file) throws EncoderIdentificationException {
        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
            return input.readAllBytes();
        } catch (IOException e) {
            throw new EncoderIdentificationException(e.getMessage());
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //endregion

}
