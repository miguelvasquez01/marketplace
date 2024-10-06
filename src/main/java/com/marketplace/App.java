package com.marketplace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private static Stage primaryStage;

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("dashboardView.fxml"));//Agregar excepcion de conexion
        
        scene = new Scene(root);
        // Cargar el archivo CSS
        //scene.getStylesheets().add(getClass().getResource("/com/marketplace/styles/style.css").toExternalForm());

        primaryStage.setTitle("Market");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        Parent newRoot = loadFXML(fxml);
        scene.setRoot(newRoot);
        primaryStage.setScene(scene);//?
        primaryStage.sizeToScene();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    //Método para los dialog
    public static void setDialog(String fxml, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = loader.load();

        //Crear un nuevo Stage
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); //Bloquear la ventana principal hasta que se cierre
        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}