package com.marketplace.services;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.model.Vendedor;

public class GetAllVendedoresService {
    
    private static final String AUTH_URL = "http://localhost:8080/vendedores/getAllVendedores";

    public List<Vendedor> getAllVendedores() throws ConnectException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Crear la solicitud HTTP POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(AUTH_URL))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            // Enviar la solicitud y recibir la respuesta
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Manejar la respuesta
            if (response.statusCode() == 200) {
                // Deserializar la respuesta JSON en un objeto Vendedor
                List<Vendedor> vendedores = objectMapper.readValue(response.body(), new TypeReference<List<Vendedor>>() {});

                System.out.println("Vendedores obtenidos correctamente");
                return vendedores;
            } else {
                System.out.println("Error al obtener los vendedores: " + response.body());//Si no encuntra el vendedor
            }
        } catch(ConnectException e) {
            throw new ConnectException();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Si falla, devuelve null
    }
}
