package com.example.ben.testspotify;

import com.google.gson.JsonElement;

import java.util.HashMap;

/**
 * Created by dave on 5/1/17.
 */

public interface IntentHandler {
    String handle(HashMap<String, JsonElement> params);
}

