package com.linkapital.core.services.integrations;

import com.google.maps.model.GeocodingResult;
import com.linkapital.core.services.integrations.contract.to.GoogleLocationTO;

import java.util.Optional;

/**
 * Interface to retrieve data from Google Maps
 */
public interface GoogleMapsService {

    /**
     * Retrieve data from first Google Maps API result obtained by the given address
     *
     * @param address {@link String} the address
     * @return {@link Optional<GoogleLocationTO>}
     */
    Optional<GoogleLocationTO> getLocation(String address);

    /**
     * Retrieve all data by the given address
     *
     * @param address {@link String} the address
     * @return {@link GeocodingResult}[]
     */
    GeocodingResult[] getAllGeocode(String address);

}
