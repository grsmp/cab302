package com.geraj.assignment;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Utility class for managing navigation and view transitions in the application.
 */
public class SceneSwitcher {
    private static String currentView = GerajApplication.LANDING_PAGE;

    /**
     * Gets the name of the currently active FXML view.
     * @return The active FXML view name as a {@link String}.
     */
    public static String getCurrentView() {
        return currentView;
    }

    /**
     * Replaces the current scene's root node with the root loaded from the specified FXML file.
     * @param <T> The type of the controller associated with the target FXML resource.
     * @param event The {@link Event} triggered by a UI component, used to resolve the current active window.
     * @param fxmlFile The name of the target FXML view.
     * @return The controller instance for the new page, or {@code null} if an {@link IOException} occurs during loading.
     */
    public static <T> T switchScene(Event event, String fxmlFile) {
        currentView = fxmlFile;
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(GerajApplication.class.getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            stage.getScene().setRoot(root);

            return fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}