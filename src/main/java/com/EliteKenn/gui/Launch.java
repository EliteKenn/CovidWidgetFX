package com.EliteKenn.gui;

import com.EliteKenn.datafetch.DataProviderService;
import com.EliteKenn.datafetch.model.CovidDataModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class Launch extends Application {

private double xOffset;
private double yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception {


        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0);
        primaryStage.show();

        Stage secondaryStage = new Stage();
        secondaryStage.initStyle(StageStyle.UNDECORATED);
        secondaryStage.initOwner(primaryStage);


        Parent root = FXMLLoader.load(getClass().getResource("/com/EliteKenn/gui/widget/widget.fxml"));
        Scene scene = new Scene(root);
        secondaryStage.setScene(scene);
        secondaryStage.show();

        //Make it right-top aligned
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        secondaryStage.setX(visualBounds.getMaxX() - 25 - scene.getWidth());
        secondaryStage.setY(visualBounds.getMinY() + 25);

        // Add support for drag-and-drop
        //Drag = mouse click + drag

        scene.setOnMousePressed(e ->{
            xOffset = secondaryStage.getX() - e.getScreenX();
            yOffset = secondaryStage.getY() - e.getScreenY();
        });

        scene.setOnMouseDragged(e ->{
            secondaryStage.setX(e.getScreenX() + xOffset);
            secondaryStage.setY(e.getScreenY() + yOffset);
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
