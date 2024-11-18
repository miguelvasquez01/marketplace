package com.marketplace.controller;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import java.util.Optional;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;

import com.marketplace.App;
import com.marketplace.model.Vendedor;
import com.marketplace.services.ChatServer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class DashBoardController {
    
    @FXML
    private VBox contentVbox;

    private Vendedor vendedorAutenticado;

    @FXML
    void btnCerrarSesion(ActionEvent event) throws IOException {
        App.setRoot("loginView");
    }

    @FXML
    void btnEstadisticas(ActionEvent event) {
        try {
            // Validar que el vendedor esté autenticado
            if (vendedorAutenticado == null || vendedorAutenticado.getCedula() == null) {
                mostrarError("Error", "No hay un vendedor autenticado");
                return;
            }

            // Crear diálogo para seleccionar mes
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Seleccionar Mes");
            dialog.setHeaderText("Seleccione el mes para el reporte");

            // Crear ComboBox con los meses
            ComboBox<String> mesComboBox = new ComboBox<>();
            mesComboBox.getItems().addAll(
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
            );
            mesComboBox.setValue("Enero"); // Valor por defecto

            // Crear el panel de contenido
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.setContent(new VBox(10, mesComboBox));
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Convertir la selección a número de mes
            dialog.setResultConverter(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    return String.valueOf(mesComboBox.getSelectionModel().getSelectedIndex() + 1);
                }
                return null;
            });

            // Mostrar diálogo y procesar resultado
            Optional<String> resultado = dialog.showAndWait();
            if (resultado.isPresent()) {
                String mes = resultado.get();
                generarReportePorMes(mes);
            }

        } catch (Exception e) {
            mostrarError("Error", "Error al preparar el diálogo: " + e.getMessage());
        }
    }

    private void generarReportePorMes(String mes) {
        try {
            // Crear directorio de reportes si no existe
            File reporteFolder = new File(System.getProperty("user.home") + File.separator + "MarketplaceReportes");
            if (!reporteFolder.exists()) {
                reporteFolder.mkdirs();
            }

            // Crear el archivo donde se guardará el reporte
            String nombreArchivo = "reporte_vendedor_" + vendedorAutenticado.getCedula() + 
                "_mes_" + mes + "_" + System.currentTimeMillis() + ".pdf";
            File reporteFile = new File(reporteFolder, nombreArchivo);
            
            // Hacer la petición HTTP al servidor incluyendo el mes
            URL url = URI.create("http://localhost:8080/reportes/generar/" + 
                vendedorAutenticado.getCedula() + "/" + mes).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Verificar la respuesta
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                guardarArchivo(conn, reporteFile);
                mostrarExito("Reporte Generado", "Reporte generado exitosamente");
                mostrarUbicacion("Ubicación del Reporte", reporteFile.getAbsolutePath());
            } else {
                mostrarError("Error", "Error en la petición HTTP: " + responseCode);
            }

        } catch (Exception e) {
            mostrarError("Error", "Error al generar el reporte: " + e.getMessage());
        }
    }

    @FXML
    void btnMiPerfil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/miPerfilView.fxml"));
            Pane newPane = loader.load();

            MiPerfilController miPerfilController = loader.getController();
            miPerfilController.setVendedor(vendedorAutenticado);

            newPane.prefWidthProperty().bind(contentVbox.widthProperty());
            newPane.prefHeightProperty().bind(contentVbox.heightProperty());

            contentVbox.getChildren().clear();
            contentVbox.getChildren().add(newPane); 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnMisContactos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/misContactosView.fxml"));
            Pane newPane = loader.load();

            MisContactosController misContactosController = loader.getController();
            misContactosController.setVendedor(vendedorAutenticado);

            newPane.prefWidthProperty().bind(contentVbox.widthProperty());
            newPane.prefHeightProperty().bind(contentVbox.heightProperty());

            contentVbox.getChildren().clear();
            contentVbox.getChildren().add(newPane);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnMisProductos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/misProductosView.fxml"));
            Pane newPane = loader.load();

            MisProductosController misProductosController = loader.getController();
            misProductosController.setVendedor(vendedorAutenticado);

            newPane.prefWidthProperty().bind(contentVbox.widthProperty());
            newPane.prefHeightProperty().bind(contentVbox.heightProperty());

            contentVbox.getChildren().clear();
            contentVbox.getChildren().add(newPane);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnChat(ActionEvent event) {
        try {
            // Iniciar el servidor si no está corriendo
            if (!ChatServer.isRunning()) {
                ChatServer.startServer();
                // Esperar un momento para que el servidor inicie
                Thread.sleep(1000);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/chatView.fxml"));
            Pane newPane = loader.load();

            ChatView chatViewController = loader.getController();
            chatViewController.setVendedor(vendedorAutenticado);

            newPane.prefWidthProperty().bind(contentVbox.widthProperty());
            newPane.prefHeightProperty().bind(contentVbox.heightProperty());

            contentVbox.getChildren().clear();
            contentVbox.getChildren().add(newPane);

        } catch (IOException | InterruptedException e) {
            mostrarError("Error", "No se pudo iniciar el chat: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnMapa(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/mapaView.fxml"));
            AnchorPane mapaView = loader.load();
            
            contentVbox.getChildren().clear();
            contentVbox.getChildren().add(mapaView);
            
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar el mapa");
            alert.setContentText("No se pudo cargar la vista del mapa: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedorAutenticado = vendedor;
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarExito(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarUbicacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void guardarArchivo(HttpURLConnection conn, File file) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            
            fileOutputStream.flush();
        } catch (IOException e) {
            throw new IOException("Error al guardar el archivo: " + e.getMessage());
        }
    }

    // Agregar método para cerrar el servidor al cerrar la aplicación
    public void shutdown() {
        ChatServer.stopServer();
    }
}
