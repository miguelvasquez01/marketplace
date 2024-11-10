package com.marketplace.controller;

import com.marketplace.model.Vendedor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class ChatView {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;
    @FXML
    private Button sendButton;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;


    private Vendedor vendedorAutenticado;

    public void initialize() {
        try {

            socket = new Socket("localhost", 12345);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            new Thread(new IncomingReader()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void EnviarMensaje() { //
        String message = messageField.getText();
        if (!message.isEmpty()) {
            out.println(message); //
            messageField.clear(); //
        }
    }

    private class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {

                    chatArea.appendText(message + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para cerrar la conexión al cerrar la aplicación
    public void closeConnection() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para recibir el vendedor autenticado
    public void setVendedor(Vendedor vendedor) {
        this.vendedorAutenticado = vendedor;
        // Si necesitas hacer algo con el vendedor, hazlo aquí
    }
}