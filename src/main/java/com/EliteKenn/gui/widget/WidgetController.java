package com.EliteKenn.gui.widget;

import com.EliteKenn.config.ConfigModel;
import com.EliteKenn.config.ConfigurationService;
import com.EliteKenn.datafetch.CovidApi;
import com.EliteKenn.datafetch.DataProviderService;
import com.EliteKenn.datafetch.model.CountryData;
import com.EliteKenn.datafetch.model.CovidDataModel;
import com.EliteKenn.datafetch.model.GlobalData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Window;

import java.lang.module.Configuration;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WidgetController implements Initializable {

    private ScheduledExecutorService executorService;
    private ConfigModel configModel;

    @FXML
    public AnchorPane rootPane;
    @FXML
    public Text txtGlobalReport;
    @FXML
    public Text txtCountryCode;
    @FXML
    public Text txtCountryReport;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            configModel = new ConfigurationService().getConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializeScheduler();
        initializeContextMenu();
        txtCountryCode.setText(configModel.getCountryCode());
    }

    private void initializeScheduler() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::loadData,0,configModel.getRefreshIntervalInSeconds(), TimeUnit.SECONDS);
    }

    private void loadData(){
        DataProviderService dataProviderService = new DataProviderService();
        CovidDataModel covidDataModel = dataProviderService.getData(configModel.getCountryName());

        Platform.runLater(() ->{
            inflateData(covidDataModel);
        });
    }

    private void inflateData(CovidDataModel covidDataModel){
        GlobalData globalData = covidDataModel.getGlobalData();
        txtGlobalReport.setText(getFormattedData(globalData.getCases(), globalData.getRecovered(), globalData.getDeaths()));
        CountryData countryData = covidDataModel.getCountryData();
        txtCountryReport.setText(getFormattedData(countryData.getCases(), countryData.getRecovered(), countryData.getDeaths()));

        readjustStage(txtCountryCode.getScene().getWindow());
    }
    private String getFormattedData(long totalCases, long recoveredCases, long deaths){
        return String.format("Cases: %-8d | Recovered: %-6d | Deaths: %-6d", totalCases, recoveredCases, deaths);
    }

    private void readjustStage(Window stage){
        stage.sizeToScene();

        //Make it right-top aligned
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(visualBounds.getMaxX() - 25 - txtCountryCode.getScene().getWidth());
        stage.setY(visualBounds.getMinY() + 25);

    }

    private void initializeContextMenu(){
        MenuItem refreshItem = new MenuItem("Refresh");

        refreshItem.setOnAction(e ->{
            executorService.schedule(this::loadData,0,TimeUnit.SECONDS);
        });
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e ->{
            System.exit(0);
        });


        final ContextMenu contextMenu = new ContextMenu(refreshItem,exitItem);
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, e ->{
            if(e.isSecondaryButtonDown()){
                contextMenu.show(rootPane,e.getScreenX(), e.getScreenY());
            }
            else{
                if(contextMenu.isShowing()){
                    contextMenu.hide();
                }
            }
        });


    }
}
