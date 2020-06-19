package com.EliteKenn.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class ConfigurationService {

    private final File SETTINGS_FILES = new File("settings.json");

    private Gson gson = new GsonBuilder().create();

    public ConfigModel getConfiguration() throws Exception{
        if(!SETTINGS_FILES.exists()) {
            createSettingsFile();
        }
        return getConfigurationFromFile();

    }

    private ConfigModel getConfigurationFromFile() throws IOException {
        ConfigModel configModel = new ConfigModel();
        try(Reader reader = new FileReader(SETTINGS_FILES)){
            return gson.fromJson(reader, ConfigModel.class);
        }

    }

    private void createSettingsFile() throws IOException {
    ConfigModel configModel = new ConfigModel();
    try(Writer writer = new FileWriter(SETTINGS_FILES, false)){
        gson.toJson(configModel,writer);
    }
    }
}
