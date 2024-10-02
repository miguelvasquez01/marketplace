package com.marketplace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @SuppressWarnings("exports")
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("marketView.fxml"));
        
        scene = new Scene(root);
        // Cargar el archivo CSS
        scene.getStylesheets().add(getClass().getResource("/com/marketplace/styles/style.css").toExternalForm());

        primaryStage.setTitle("Market");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // @SuppressWarnings("exports")
    // @Override
    // public void start(Stage stage) throws IOException {
    //     scene = new Scene(loadFXML("marketView"), 874, 782);
    //     stage.setScene(scene);
    //     stage.show();
    // }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}