package com.example.shafayat.prcacticewithapi;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shafayat.prcacticewithapi.data.Channel;
import com.example.shafayat.prcacticewithapi.service.WeatherServiceCallback;
import com.example.shafayat.prcacticewithapi.service.YahooWeatherCallBack;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView weatherIconImgeview;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private YahooWeatherCallBack service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherIconImgeview = (ImageView)findViewById(R.id.weatherImageView);
        temperatureTextView = (TextView)findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView)findViewById(R.id.conditionTextView);
        locationTextView = (TextView)findViewById(R.id.locactionTextView);

        service = new YahooWeatherCallBack(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        service.refreshWeather("Austin, TX");
    }

    @Override
    public void serviceSuccess(Channel channel) {

        dialog.hide();

        int resource = getResources().getIdentifier("drawable/icon_"+channel.getItem().getCondition().getCode(), null, getPackageName());

        Drawable weatherIconDrawable = getResources().getDrawable(resource);
        weatherIconImgeview.setImageDrawable(weatherIconDrawable);

        locationTextView.setText(service.getLocation());
        conditionTextView.setText(channel.getItem().getCondition().getDescription());
        temperatureTextView.setText(channel.getItem().getCondition().getTemperature()+' '+channel.getUnits().getTemperature());
    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
