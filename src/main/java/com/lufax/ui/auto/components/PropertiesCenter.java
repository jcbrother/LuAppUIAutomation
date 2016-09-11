package com.lufax.ui.auto.components;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Jc on 16/9/11.
 */

@Scope("singleton")
@Component
public class PropertiesCenter {

    @Autowired
    public PropertiesFetcher propertiesFetcher;

    public HashMap<String,String> runConfigs = null;
    public HashMap<String,JSONObject> deviceConfigs = null;
    public HashMap<String,JSONObject> dbConfigs = null;

    public synchronized PropertiesCenter init() throws IOException {
        if (runConfigs == null && deviceConfigs == null && dbConfigs == null) {
            propertiesFetcher.fetchAllConfigs();
            this.runConfigs = propertiesFetcher.runConfigs;
            this.deviceConfigs = propertiesFetcher.deviceConfigs;
            this.dbConfigs = propertiesFetcher.dbConfigs;
        }
        return this;
    }

    public HashMap<String, String> getRunConfigs() {
        return runConfigs;
    }

    public HashMap<String, JSONObject> getDeviceConfigs() {
        return deviceConfigs;
    }

    public HashMap<String, JSONObject> getDbConfigs() {
        return dbConfigs;
    }
}
