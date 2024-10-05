package com.marketplace.services;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.model.Vendedor;

public class RegisterService {

    private static final String AUTH_URL = "http://localhost:8080/vendedores/guardarVendedor";

    public boolean register(String nombre,String apellidos, String cedula, String direccion) throws ConnectException { // Cambiado para devolver un Vendedor
        Vendedor vendedor = Vendedor.builder().apellidos(apellidos).nombre(nombre).direccion(direccion).cedula(cedula).build();

        try {
            // Convertir el objeto LoginRequest a JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(vendedor);

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
                System.out.println("Vendedor creado correctamente");
                return true;
            } else {
                System.out.println("Error de creación de vendedor: " + response.body());
            }

        } catch(ConnectException e) {
            throw new ConnectException();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; //Si la cédula ya existe
    }
}