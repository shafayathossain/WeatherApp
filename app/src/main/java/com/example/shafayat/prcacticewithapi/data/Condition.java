package com.example.shafayat.prcacticewithapi.data;

import org.json.JSONObject;

/**
 * Created by Shafayat on 7/12/2017.
 */

public class Condition implements JSONPopulator {

    private int code;
    private int temperature;
    private String description;

    public int getCode(){

        return code;
    }

    public int getTemperature(){

        return temperature;
    }

    public String getDescription(){

        return description;
    }

    @Override
    public void populate(JSONObject data) {

        if(data != null) {
            code = data.optInt("code");
            temperature = data.optInt("temp");
            description = data.optString("text");
        }

    }
}
