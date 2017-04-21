package com.example.ben.testspotify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import org.w3c.dom.Text;

import java.util.List;


import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;

import retrofit.client.Response;
import retrofit.Callback;

import java.util.concurrent.TimeUnit;

*/

public class MainActivity extends AppCompatActivity  {


  /*  private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "abd357acc5ce43c5acd09bccddf8df9b";
    private static final String REDIRECT_URI = "rsps://callback/";
    private static final String MY_ACCESS_TOKEN = "access_token";
2017.04.21*/
    static final String EXTRA_TOKEN = "EXTRA_TOKEN";
    private static final String KEY_CURRENT_QUERY = "CURRENT_QUERY";

/*2017.04.21 afternoon
    private String token;
    private String client_id;
    private final List<Track> mItems = new ArrayList<>();

    private SpotifyApi spotifyApi;
    private SpotifyService mSpotifyApi;
    private String query = "beethoven";
    private Map<String,Object> options = new HashMap<String,Object>();
    private Player mPlayer;

*/
    private String token;
    private String client_id;
    private SearchAndPlay searchAndPlay;
    private Map<String,Object> options = new HashMap<String,Object>();
    private TextView resultTextView;
 //   private EditText inputEditText;

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView)findViewById(R.id.resultTextView);
      //  inputEditText = (EditText)findViewById(R.id.inputEditText);
     /*   AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
2017.04.21*/


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

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
 /*2017.04.20       if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(MainActivity.this);
                        mPlayer.addNotificationCallback(MainActivity.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });

                CredentialsHandler.setToken(this, response.getAccessToken(), response.getExpiresIn(), TimeUnit.SECONDS);

                token = response.getAccessToken();
                spotifyApi = new SpotifyApi();
                spotifyApi.setAccessToken(token);
                mSpotifyApi = spotifyApi.getService();*/
/*2017.04.20
                mSpotifyApi.searchTracks(query, options, new SpotifyCallback<TracksPager>() {
                    @Override
                    public void failure(SpotifyError spotifyError) {
                        Log.d("Here", "That's a failure");
                    }

                    @Override
                    public void success(TracksPager tracksPager, Response response) {
                        Log.d("Here", "This is a test");
                        mItems.addAll(tracksPager.tracks.items);
                    }
                });*/
     //       }

//    }*/

    @Override
    protected void onDestroy() {
  //      Spotify.destroyPlayer(this);
        searchAndPlay.withDestroy();
        super.onDestroy();
    }
/*2017.04.21 afternoon
    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MainActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("MainActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
     //   mPlayer.playUri(null,"spotify:track:0rxA51EJiKiK6JgSgGKsog",0,0);

    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Error error) {
        Log.d("MainActivity", "Logged failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }
*/
    public void startPlayMusic(final View view){
        //mPlayer.playUri(null,"spotify:track:0rxA51EJiKiK6JgSgGKsog",0,0);
//        query = inputEditText.getText().toString();
/*2017.04.21 afternoon
        Log.d("hey","you guy");
        mSpotifyApi.searchTracks(query, options, new SpotifyCallback<TracksPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.d("Here", "That's a failure");
            }

            @Override
            public void success(TracksPager tracksPager, Response response) {
                Log.d("Here", "This is a test");
                mItems.addAll(tracksPager.tracks.items);
            }
        });*/
     /*   Track item = mItems.get(1);
        resultTextView.setText(item.name);
        String url = "spotify:track:" + item.id;
        mPlayer.playUri(null,url,0,0);
*/
        resultTextView.setText(searchAndPlay.getData());
        searchAndPlay.playMusic();
    }

    public void resumePlayMusic(final View view){
        searchAndPlay.resumeMusic();
    }


    public void stopPlayMusic(final View view){
    //    mPlayer.pause(null);
        /*2017.04.21 afternoon
        Track item = mItems.get(0);
        resultTextView.setText(item.name);
        String url = "spotify:track:" + item.id;
        mPlayer.playUri(null,url,0,0);*/
//        resultTextView.setText(searchAndPlay.getData());
        searchAndPlay.stopMusic();
    }
/*2017.04.21 afternoon
    public void searchTrack(String stQuery,Map<String,Object> mOptions){
        mSpotifyApi.searchTracks(stQuery, mOptions, new SpotifyCallback<TracksPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.d("Here", "That's a failure");
            }

            @Override
            public void success(TracksPager tracksPager, Response response) {
                Log.d("Here", "This is a test");
                mItems.addAll(tracksPager.tracks.items);
            }
        });



    }

    public void initPlay(String sToken,String sId){
        Config playerConfig=new Config(this,sToken,sId);
        Spotify.getPlayer(playerConfig,this,new SpotifyPlayer.InitializationObserver(){
        @Override
        public void onInitialized(SpotifyPlayer spotifyPlayer){
               mPlayer=spotifyPlayer;
               mPlayer.addConnectionStateCallback(MainActivity.this);
               mPlayer.addNotificationCallback(MainActivity.this);
        }

        @Override
        public void onError(Throwable throwable){
                Log.e("MainActivity","Could not initialize player: "+throwable.getMessage());
        }
        });

        spotifyApi=new SpotifyApi();
        spotifyApi.setAccessToken(sToken);
        mSpotifyApi=spotifyApi.getService();
    }*/
}
