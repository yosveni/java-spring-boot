package com.linkapital.captcha;

import com.DeathByCaptcha.Client;
import com.DeathByCaptcha.SocketClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class CaptchaSolver {

    private final Client client;

    public CaptchaSolver(@Value("${api_integration.robot.death_captcha.username}") String username,
                         @Value("${api_integration.robot.death_captcha.password}") String password) {
        this.client = new SocketClient(username, password);
    }

    public CaptchaMetadata solve(InputStream inputStream) throws CaptchaErrorException {
        try {
            var captcha = client.decode(inputStream);
            if (captcha.isSolved() && captcha.isCorrect())
                return CaptchaMetadata
                        .builder()
                        .id(captcha.id)
                        .text(captcha.text)
                        .build();
            else
                throw new CaptchaErrorException("Cannot resolve the captcha");
        } catch (Exception e) {
            throw new CaptchaErrorException(e.getMessage());
        }
    }

    public void report(int id) throws CaptchaErrorException {
        try {
            client.report(id);
        } catch (Exception e) {
            throw new CaptchaErrorException(e.getMessage());
        }
    }

}
