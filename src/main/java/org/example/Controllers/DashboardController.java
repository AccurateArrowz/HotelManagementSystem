package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    @FXML
    private LineChart<String, Integer> lineChart;
    @FXML
    private Hyperlink icons8;
    @FXML
    private Hyperlink icons7;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeLineChart();
    }

    public void initializeLineChart() {
            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data("Sunday",  8));
        series.getData().add(new XYChart.Data("Monday",  6));
        series.getData().add(new XYChart.Data("Tuesday",  7));
        series.getData().add(new XYChart.Data("Wednesday",  8));
        series.getData().add(new XYChart.Data("Thursday",  4));
        series.getData().add(new XYChart.Data("Friday",  9));
        series.getData().add(new XYChart.Data("Saturday",  8));
        lineChart.getData().addAll(series);
        lineChart.lookup( ".chart-plot-background").setStyle("-fx-background-color: transparent;");
        series.getNode().setStyle("-fx-stroke: #d6e8ff");
    }
    public void openLinks(URI uri) throws IOException {
        Desktop.getDesktop().browse(uri);
    }



}