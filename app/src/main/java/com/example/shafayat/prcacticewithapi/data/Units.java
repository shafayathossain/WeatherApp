package com.example.shafayat.prcacticewithapi.data;

import org.json.JSONObject;

/**
 * Created by Shafayat on 7/12/2017.
 */

public class Units implements JSONPopulator{

    private String temperature;

    public String getTemperature(){
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {

        temperature = data.optString("temperature");
    }
}
