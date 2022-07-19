package com.linkapital.jucesp.bots.jucesp;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class SPJucespCredentialsGenerator {

    private static SPJucespCredentialsGenerator INSTANCE;
    private List<SPJucespCredentials> credentials;
    private int lastPosition;

    private SPJucespCredentialsGenerator() {
        this.credentials = new ArrayList<>();
        this.fillCredentials();
    }

    static SPJucespCredentialsGenerator getInstance() {
        if (Objects.isNull(INSTANCE))
            INSTANCE = new SPJucespCredentialsGenerator();

        return INSTANCE;
    }

    private void fillCredentials() {
        this.credentials.add(SPJucespCredentials.builder().cpf("24246324809").password("Cubano1404").build());
        this.credentials.add(SPJucespCredentials.builder().cpf("24302834846").password("Cubana1301").build());
        this.credentials.add(SPJucespCredentials.builder().cpf("80151528950").password("Mn1234567*").build());
        this.credentials.add(SPJucespCredentials.builder().cpf("80151524963").password("Mn1234567*").build());
        this.credentials.add(SPJucespCredentials.builder().cpf("29674360700").password("TIKUNolan24").build());
    }

    public SPJucespCredentials getCredentials() {
        var r = new Random();
        var value = r.nextInt(credentials.size());

        while (lastPosition == value)
            value = r.nextInt(credentials.size());

        lastPosition = value;

        return credentials.get(lastPosition);
    }

}
