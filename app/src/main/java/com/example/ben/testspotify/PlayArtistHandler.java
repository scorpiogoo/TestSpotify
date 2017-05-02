package com.example.ben.testspotify;

import com.google.gson.JsonElement;
import java.util.HashMap;

/**
 * Created by ben on 2017/5/2.
 */

public class PlayArtistHandler implements IntentHandler {
    PlayArtistHandler() {
    }

    @Override
    public String handle(HashMap<String, JsonElement> params) {
        JsonElement element = params.get("artist");
        String query = element.getAsString();

        return query;
    }
}
