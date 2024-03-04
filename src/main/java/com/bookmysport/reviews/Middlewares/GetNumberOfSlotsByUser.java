package com.bookmysport.reviews.Middlewares;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bookmysport.reviews.Models.ResponseModelForNumberOfSlots;

import reactor.core.publisher.Mono;

@Service
public class GetNumberOfSlotsByUser {

    @Autowired
    private WebClient webClient;

    @Autowired
    private ResponseModelForNumberOfSlots responseModelForNumberOfSlots;

    public ResponseEntity<ResponseModelForNumberOfSlots> getNumberOfSlotsOfAnUser(String token, String role) {
        try {
            Mono<List<Object>> userDetailsMono = webClient.get()
                    .uri(System.getenv("NUMBER_OF_SLOTS_OF_USER"))
                    .headers(headers -> {
                        headers.set("token", token);
                        headers.set("role", role);
                    })
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Object>>() {
                    });

            List<Object> listOfSlotsOfAnUser = userDetailsMono.block();

            responseModelForNumberOfSlots.setSuccess(true);
            responseModelForNumberOfSlots.setNumberOfSlots(listOfSlotsOfAnUser.size());

            return ResponseEntity.status(HttpStatus.OK).body(responseModelForNumberOfSlots);

        } catch (Exception e) {

            responseModelForNumberOfSlots.setSuccess(false);
            responseModelForNumberOfSlots.setMessage("Internal Server Error in GetNumberOfSlotsByUser.java. Reason: "+e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseModelForNumberOfSlots);
        }
    }
}
