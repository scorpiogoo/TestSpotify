package com.example.ben.testspotify;

import java.util.Locale;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import static android.speech.tts.TextToSpeech.QUEUE_ADD;


/**
 * Created by dave on 4/19/17.
 */

public class StringSpeaker {

    private TextToSpeech tts;

    public StringSpeaker(Context context) {

        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });
    }

    public void pronounce(String speech) {
        tts.speak(speech, QUEUE_ADD, null, "7");            // DAJ - this is not right, but it works for now.  The last parameter should be unique for each string added to the queue.
    }

}
