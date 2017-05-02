package com.example.ben.testspotify;

import com.google.gson.JsonElement;

import java.util.HashMap;

/**
 * Created by dave on 5/1/17.
 */

public class IntentHandlerFactory {

    static IntentHandlerFactory theInstance = null;

    IntentHandlerFactory() {
    }

    static IntentHandler createHandler(String action, HashMap<String, JsonElement> params) {

        IntentHandler rv = null;

        if(theInstance == null) {
            theInstance = new IntentHandlerFactory();
        }

        switch(action) {
            case "PlayTrack":
                rv = new PlayTrackHandler();
                break;

 /*
            case "PlayPlaylist":
                rv = new PlayPlaylistHandler();
                break;
            case "PlayAlbum":
                rv = new PlayAlbumHandler();
                break;
            case "PlayArtist":
                rv = new PlayArtistHandler();
                break;
 */
        }

        return rv;
    }

}
