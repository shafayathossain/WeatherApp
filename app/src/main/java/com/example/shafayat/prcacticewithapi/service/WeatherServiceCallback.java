package com.example.shafayat.prcacticewithapi.service;

import com.example.shafayat.prcacticewithapi.data.Channel;

/**
 * Created by Shafayat on 7/12/2017.
 */

public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
