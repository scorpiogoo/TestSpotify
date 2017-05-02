package com.example.ben.testspotify;

import android.content.Context;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Fulfillment;
import ai.api.model.Result;

/**
 * Created by dave on 4/19/17.
 */

public class ApiAiShim implements AIListener {

    private AIService aiService;
    private ApiAiShimListener shimListener;

    public ApiAiShim(Context context, String clientToken, ApiAiShimListener listener) {

        shimListener = listener;

//        final AIConfiguration.SupportedLanguages lang = AIConfiguration.SupportedLanguages.fromLanguageTag(selectedLanguage.getLanguageCode());
        final AIConfiguration config = new AIConfiguration(clientToken, //"9e27dbcf8e22448c9fdb81829b4df019",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(context, config);
        aiService.setListener(this);
    }

    public void startRecognition(){

        aiService.startListening();
    }

    public void stopRecognition(){

        aiService.stopListening();
    }

    @Override
    public void onResult(AIResponse response) {
        final Result result = response.getResult();
        final String action = result.getAction();
        final Fulfillment fulfillment = result.getFulfillment();
        final String speech = fulfillment.getSpeech();

        shimListener.ApiAiResult(speech, action, result.getParameters());
    }

    @Override
    public void onError(AIError error) {
        shimListener.ApiAiError(error.toString());
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {
        shimListener.ApiAiUIOn();
    }

    @Override
    public void onListeningCanceled() {
        shimListener.ApiAiUIOff();
    }

    @Override
    public void onListeningFinished() {

    }
}
