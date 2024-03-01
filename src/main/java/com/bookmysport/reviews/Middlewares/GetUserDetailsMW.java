package com.bookmysport.reviews.Middlewares;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class GetUserDetailsMW {

    @Autowired
    private WebClient webClient;

    public ResponseEntity<Map<String, Object>> getUserDetails(String token, String role) {
        try {
            Mono<Map<String, Object>> userDetailsMono = webClient.get()
                    .uri(System.getenv("AUTH_SERVICE_URL"))
                    .headers(headers -> {
                        headers.set("Content-Type", "application/json");
                        headers.set("token", token);
                        headers.set("role", role);
                    })
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            Map<String, Object> userDetails = userDetailsMono.block();

            if (userDetails == null) {
                Map<String, Object> response = new HashMap<>();

                response.put("success", false);
                response.put("message", "User doesn't exist");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            else
            {
                return ResponseEntity.ok().body(userDetails);
            }

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();

            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
