package com.linkapital.core.services.integrations.contract;

import com.google.maps.model.GeocodingResult;
import com.linkapital.core.services.integrations.contract.to.GoogleLocationTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GoogleMapsBinder {

    GoogleMapsBinder GOOGLE_MAPS_BINDER = Mappers.getMapper(GoogleMapsBinder.class);

    @Mapping(target = "latitude", expression = "java(source.geometry.location.lat)")
    @Mapping(target = "longitude", expression = "java(source.geometry.location.lng)")
    GoogleLocationTO bindGoogleLocationTO(GeocodingResult source);

}
