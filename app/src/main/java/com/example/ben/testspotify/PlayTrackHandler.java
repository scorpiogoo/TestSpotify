package com.example.ben.testspotify;

import com.google.gson.JsonElement;
import java.util.HashMap;

/**
 * Created by dave on 5/1/17.
 */

public class PlayTrackHandler implements IntentHandler {

    PlayTrackHandler() {
    }

    @Override
    public String handle(HashMap<String, JsonElement> params) {
        JsonElement element = params.get("track");
        String query = element.getAsString();

        return query;
    }
}
