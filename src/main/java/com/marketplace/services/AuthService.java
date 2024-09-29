package com.marketplace.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.model.Vendedor;

public class AuthService {

    private static final String AUTH_URL = "http://localhost:8080/auth/Vendedor";

    public Vendedor login(String username, String password) { // Cambiado para devolver un Vendedor
        LoginRequest loginRequest = new LoginRequest(username, password);

        try {
            // Convertir el objeto LoginRequest a JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(loginRequest);

            // Crear la solicitud HTTP POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(AUTH_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            // Enviar la solicitud y recibir la respuesta
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Manejar la respuesta
            if (response.statusCode() == 200) {
                // Deserializar la respuesta JSON en un objeto Vendedor
                Vendedor vendedor = objectMapper.readValue(response.body(), Vendedor.class);
                System.out.println("Login exitoso para: " + vendedor.getNombre());
                return vendedor; // Devuelve el objeto Vendedor
            } else {
                System.out.println("Error de autenticación: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Si la autenticación falla, devuelve null
    }
}