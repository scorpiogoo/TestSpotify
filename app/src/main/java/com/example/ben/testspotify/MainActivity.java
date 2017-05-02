package com.example.ben.testspotify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.view.View;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




public class MainActivity extends AppCompatActivity implements ApiAiShimListener {



    private ApiAiShim nlp;
    private StringSpeaker voice;
    private PermissionRequester permRequester;
    private static final String clientToken = "a6e2843793354ff5840c8b815b1dcb21";
    static final String EXTRA_TOKEN = "EXTRA_TOKEN";
    private static final String KEY_CURRENT_QUERY = "CURRENT_QUERY";

    public static final String TAG = MainActivity.class.getName();



    private String token;
    private String client_id;
    private SearchAndPlay searchAndPlay;
    private Map<String,Object> options = new HashMap<String,Object>();
    private TextView resultTextView;
    private ImageView recListener;
 //   private EditText inputEditText;

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView)findViewById(R.id.resultTextView);
        recListener = (ImageView)findViewById(R.id.recListener);
        nlp = new ApiAiShim(this,clientToken,this);
        voice = new StringSpeaker(this);
        permRequester = new PermissionRequester(this);
        permRequester.request(Manifest.permission.RECORD_AUDIO);



        searchAndPlay = new SearchAndPlay(MainActivity.this);
        Intent intent = getIntent();
        ArrayList<String> stringList = (ArrayList<String>) intent.getStringArrayListExtra(EXTRA_TOKEN);
        token = stringList.get(0);
        client_id = stringList.get(1);
        searchAndPlay.initPlay(token,client_id);

        final SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAndPlay.searchTrack(query,options);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        permRequester.storeResult(requestCode, permissions[0], grantResults);
    }



    @Override
    protected void onDestroy() {
  //      Spotify.destroyPlayer(this);
        searchAndPlay.withDestroy();
        super.onDestroy();
    }


    public void ApiAiError(final String error){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                resultTextView.setText(error);
            }
        });
    }

    public void ApiAiResult(String speech,String intent,HashMap<String,JsonElement> params){
        String sQuery = "";
        resultTextView.setText(speech);
        voice.pronounce(speech);


        // Just for debugging.
        if (params != null && !params.isEmpty()) {
            Log.i(TAG, "Parameters: ");
            for (final Map.Entry<String, JsonElement> entry : params.entrySet()) {
                Log.i(TAG, String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
            }
        }

        if(!intent.equals("PlayNext") ) {
            IntentHandler handler = IntentHandlerFactory.createHandler(intent, params);
            if (handler != null)
                sQuery = handler.handle(params);
            searchAndPlay.searchTrack(sQuery, options);
        }else{
            searchAndPlay.playNextMusic();
        }


    }


    public void ApiAiUIOn(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recListener.setVisibility(View.VISIBLE);
            }
        });
    }

    public void ApiAiUIOff(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recListener.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void startPlayMusic(final View view){

        nlp.startRecognition();
    }

    public void resumePlayMusic(final View view){
       // searchAndPlay.playMusic();
        searchAndPlay.stopMusic();
    }


    public void stopPlayMusic(final View view){

        nlp.stopRecognition();
    }




}
