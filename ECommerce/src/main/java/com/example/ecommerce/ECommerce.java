package com.example.ecommerce;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ECommerce extends Application {
    UserInterface content = new UserInterface();
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(content.createContent()); // base layout
        stage.setTitle("Ecommerce"); // Title of the application
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}