package fr.geoking.xstudio;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;


public class XStudioUi extends Application {
    private File xmlFile;
    private File xsdFile;
    private TextArea resultArea;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("XML Validator");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Button xmlButton = new Button("Choose XML File");
        Button xsdButton = new Button("Choose XSD File");
        Button validateButton = new Button("Validate");
        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefHeight(200);
        resultArea.setPrefWidth(400);
        resultArea.setWrapText(true);

        xmlButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open XML File");
            xmlFile = fileChooser.showOpenDialog(primaryStage);
            if (xmlFile != null) {
                 System.out.println("Selected XML File: " + xmlFile.getAbsolutePath());
            }
        });

        xsdButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open XSD File");
            xsdFile = fileChooser.showOpenDialog(primaryStage);
            if (xsdFile != null) {
                System.out.println("Selected XSD File: " + xsdFile.getAbsolutePath());
            }
        });

        validateButton.setOnAction(e -> {
            if (xmlFile == null || xsdFile == null) {
                resultArea.setText("Please select both XML and XSD files.");
                return;
            }

            XmlValidator validator = new XmlValidator();
            List<String> errors = validator.validateXml();
            resultArea.clear();
            if (errors.isEmpty()) {
                resultArea.setText("XML is valid against the XSD.");
            } else {
                errors.forEach(error -> resultArea.appendText(error + "\n"));
            }
        });

        grid.add(xmlButton, 0, 0);
        grid.add(xsdButton, 0, 1);
        grid.add(validateButton, 0, 2);
        grid.add(resultArea, 0, 3);
        primaryStage.setScene(new Scene(grid, 500, 400));
        primaryStage.show();
    }
}