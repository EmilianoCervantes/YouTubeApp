package com.example.ovman.youtubeapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

//Cambiamos Activity por YouTubeBaseActivity
public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView youTubePlayerView;
    //Declaramos la licencia/key
    private String key = "AIzaSyB1IcJoej1Z3_JtsvhoVdUdKi0JFeKbsc8";
    //Un video, el que sea, solo el id
    String video = "D86RtevtfrA";

    //Lista de reporoduccion
    private YouTubePlayer myYoutube;
    private EditText editText;
    private Button agregar;

    //Historial rep
    private Button verHistorial;

    ArrayList<String> list = new ArrayList<>();
    //Manejara el ListView
    ArrayAdapter<String> adapter;
    private ListView historial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Vamos por el view
        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.you);
        youTubePlayerView.initialize(key, this);

        editText = (EditText)findViewById(R.id.idVideo);
        agregar = (Button)findViewById(R.id.add);

        //Ver historial rep
        verHistorial = (Button)findViewById(R.id.verHistorial);
        historial = (ListView)findViewById(R.id.historialRep);
        verHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        historial.setAdapter(adapter);
    }

    //Si se logra la conexion
    //Da un provider y un player
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        //Mensaje de success
        Toast.makeText(this,"Youtube Enabled",Toast.LENGTH_LONG).show();
        myYoutube = youTubePlayer;
        //Evitar que se reinicie el video cuando gires la pantalla
        if (!b){
            //En la firma vamos a poner que el player reproduzca nuestro video.
            youTubePlayer.cueVideo(video);
        }
        adapter.add(video);
        //Avisar que se actualizo
        adapter.notifyDataSetChanged();
    }

    public void addReproduccion(View view){
        //Reproducir nuevo video
        myYoutube.cueVideo(editText.getText().toString());
        //Agregar al historial
        adapter.add(editText.getText().toString());
        adapter.notifyDataSetChanged();
    }

    //Si falla conectarse con YouTube
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        //Mensajes en caso de que falle
        Toast.makeText(this,"Youtube connection failed, check your Internet conmection.",Toast.LENGTH_LONG).show();
    }
}