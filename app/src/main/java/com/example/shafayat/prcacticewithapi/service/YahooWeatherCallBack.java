package com.example.shafayat.prcacticewithapi.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.shafayat.prcacticewithapi.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Shafayat on 7/12/2017.
 */

public class YahooWeatherCallBack {
    private WeatherServiceCallback callback;
    private String location;

    private Exception error;

    public YahooWeatherCallBack(WeatherServiceCallback callback){
        this.callback = callback;
    }

    public String getLocation(){
        return location;
    }

    public void refreshWeather(final String location){
        new AsyncTask<String, Void, String>(){

            @Override
            protected String doInBackground(String... params) {

                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")", location);
                String endpoints = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endpoints);
                    URLConnection urlConnection = url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    return result.toString();
                } catch (Exception e) {
                    error = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s){

                if(s == null && error != null){
                    callback.serviceFailure(error);
                    return;
                }
                try {
                    JSONObject data = new JSONObject(s);
                    JSONObject queryResults = data.optJSONObject("query");
                    int cont = queryResults.optInt("count");

                    if(cont == 0){
                        callback.serviceFailure(new LocationWeatherExcepton("Not found"));
                        return;
                    }

                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));
                    callback.serviceSuccess(channel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute(location);
    }

    public class LocationWeatherExcepton extends Exception{
        public LocationWeatherExcepton(String detailMessages){
            super(detailMessages);
        }

    }
}
