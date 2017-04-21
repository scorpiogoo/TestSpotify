package com.example.ben.testspotify;

/**
 * Created by ben on 2017/4/21.
 */

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.client.Response;
import retrofit.Callback;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;



public class SearchAndPlay implements SpotifyPlayer.NotificationCallback,ConnectionStateCallback{


    private final List<Track> mItems = new ArrayList<>();

    private SpotifyApi spotifyApi;
    private SpotifyService mSpotifyApi;

    private Player mPlayer;
    private Context context;

    public SearchAndPlay(Context cContext) {
        context = cContext;
    }

    public void initPlay(String sToken,String sId){
        Config playerConfig=new Config(context.getApplicationContext(),sToken,sId);
        Spotify.getPlayer(playerConfig,context.getApplicationContext(),new SpotifyPlayer.InitializationObserver(){
            @Override
            public void onInitialized(SpotifyPlayer spotifyPlayer){
                mPlayer=spotifyPlayer;
                mPlayer.addConnectionStateCallback(SearchAndPlay.this);
                mPlayer.addNotificationCallback(SearchAndPlay.this);
            }

            @Override
            public void onError(Throwable throwable){
                Log.e("SearchAndPlay","Could not initialize player: "+throwable.getMessage());
            }
        });

        spotifyApi=new SpotifyApi();
        spotifyApi.setAccessToken(sToken);
        mSpotifyApi=spotifyApi.getService();
    }

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

    public void playMusic(){
        Track item = mItems.get(0);
        String url = "spotify:track:" + item.id;
        mPlayer.playUri(null,url,0,0);
    }

    public void resumeMusic(){
        mPlayer.resume(null);
    }

    public void stopMusic(){
        mPlayer.pause(null);
    }

    public String getData(){
        return mItems.get(0).name;
    }

    public void withDestroy() {
        Spotify.destroyPlayer(context);
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("SearchAndPlay", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("SearchAndPlay", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("SearchAndPlay", "User logged in");
        //   mPlayer.playUri(null,"spotify:track:0rxA51EJiKiK6JgSgGKsog",0,0);

    }

    @Override
    public void onLoggedOut() {
        Log.d("SearchAndPlay", "User logged out");
    }

    @Override
    public void onLoginFailed(Error error) {
        Log.d("SearchAndPlay", "Logged failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("SearchAndPlay", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("SearchAndPlay", "Received connection message: " + message);
    }
}
