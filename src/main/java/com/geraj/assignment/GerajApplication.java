package com.geraj.assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GerajApplication extends Application {
    // Constants defining the window title and size
    public static final String TITLE = "CAB302 GERAJ Application";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;
    public static final String LANDING_PAGE = "landing-view.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GerajApplication.class.getResource(LANDING_PAGE));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }
}
