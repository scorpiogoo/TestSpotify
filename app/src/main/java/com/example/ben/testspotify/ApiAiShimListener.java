package com.example.ben.testspotify;

import com.google.gson.JsonElement;

import java.util.HashMap;

/**
 * Created by dave on 4/19/17.
 */


public interface ApiAiShimListener {

    // method signatures
    void ApiAiError (String error);
    void ApiAiResult(String speech, String intent, HashMap<String, JsonElement> params);
    void ApiAiUIOn();
    void ApiAiUIOff();
}