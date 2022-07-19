package com.linkapital.core.services.integrations.impl;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.linkapital.core.services.integrations.GoogleMapsService;
import com.linkapital.core.services.integrations.contract.to.GoogleLocationTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static com.linkapital.core.services.integrations.contract.GoogleMapsBinder.GOOGLE_MAPS_BINDER;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

@Service
public class GoogleMapsServiceImpl implements GoogleMapsService {

    private final GeoApiContext geoApiContext;

    public GoogleMapsServiceImpl(@Value("${api_integration.google.maps.key}") String googleMapsKey) {
        this.geoApiContext = new GeoApiContext.Builder()
                .apiKey(googleMapsKey)
                .maxRetries(2)
                .connectTimeout(10L, SECONDS)
                .build();
    }

    public Optional<GoogleLocationTO> getLocation(String address) {
        var geocode = this.getAllGeocode(address);

        return isNotEmpty(geocode) ?
                Optional.of(GOOGLE_MAPS_BINDER.bindGoogleLocationTO(geocode[0])) :
                Optional.empty();
    }

    public GeocodingResult[] getAllGeocode(String address) {
        var results = new GeocodingResult[0];

        try {
            results = GeocodingApi
                    .geocode(geoApiContext, address)
                    .await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ApiException | IOException e) {
            e.printStackTrace();
        }

        return results;
    }

}
