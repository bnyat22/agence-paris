package com.example.demo;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class PalaceClient extends WebServiceGatewaySupport {
    public GetHotelResponse getChambres(GetHotelRequest request) {
        GetHotelResponse response = (GetHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }
    public GetReservationResponse getReservation(GetReservationRequest request) {
        GetReservationResponse response = (GetReservationResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }
}
