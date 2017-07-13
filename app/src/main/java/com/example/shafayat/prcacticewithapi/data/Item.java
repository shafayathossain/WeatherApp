package com.example.shafayat.prcacticewithapi.data;

import org.json.JSONObject;

/**
 * Created by Shafayat on 7/12/2017.
 */

public class Item implements JSONPopulator {

    private Condition condition;

    @Override
    public void populate(JSONObject data) {

        condition = new Condition();
        condition.populate(data.optJSONObject("Condition"));

    }
}