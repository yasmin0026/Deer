package sv.edu.usam.deer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sv.edu.usam.deer.Adapters.ContenidosAdapter;
import sv.edu.usam.deer.Models.Contenido;

public class VideoContenido extends AppCompatActivity {

    private String id_video = "";
    private RequestQueue requestQueue;
    private TextView txtTitulo,txtArtista,txtAlbum,txtGenero,txtFecha;

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private String URL_ALBUM = "";

    //a list to store all the content
    List<Contenido> contenidoList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_contenido);

        txtTitulo = findViewById(R.id.tvTitle);
        txtArtista = findViewById(R.id.tvArtist);
        txtAlbum = findViewById(R.id.tvAlbum);
        txtGenero = findViewById(R.id.tvGenre);
        txtFecha = findViewById(R.id.tvDate);

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                id_video = null;
            } else {
                id_video = extra.getString("video_id");
            }
        } else {
            id_video = (String) savedInstanceState.getSerializable("video_id");
        }

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = id_video;
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        loadDatos("https://deervideo2021.000webhostapp.com/Deer_API/api/video_contenido.php?link_video=" + id_video);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.playlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //initializing the list
        contenidoList = new ArrayList<>();
    }

    private void loadDatos(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for(int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        txtTitulo.setText(jsonObject.getString("nombre_cancion"));
                        txtArtista.setText(jsonObject.getString("artista"));
                        txtAlbum.setText(jsonObject.getString("album"));
                        txtGenero.setText(jsonObject.getString("genero"));
                        txtFecha.setText(jsonObject.getString("fecha_lanzamiento"));

                        loadContenidos(jsonObject.getString("artista"),jsonObject.getString("album"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    txtArtista.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(txtArtista.getText().toString() == "Loanding....") {
                                Toast.makeText(getApplicationContext(), "Espere...", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(VideoContenido.this, AlbumsByArtista.class);
                                intent.putExtra("artista", txtArtista.getText());
                                startActivity(intent);
                            }
                        }
                    });

                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void loadContenidos(String artista, String album) {
        artista.replace(" ","%20");
        album.replace(" ","%20");
        URL_ALBUM = "https://deervideo2021.000webhostapp.com/Deer_API/api/playerlist.php?artist="+ artista +"&album=" + album;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ALBUM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);

                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject contenido = array.getJSONObject(i);

                        //adding the product to product list
                        contenidoList.add(new Contenido(
                                contenido.getString("id_contenido"),
                                contenido.getString("nombre_cancion"),
                                contenido.getString("artista"),
                                contenido.getString("album"),
                                contenido.getString("genero"),
                                contenido.getString("fecha_lanzamiento"),
                                contenido.getString("link_video"),
                                contenido.getString("portada")
                        ));
                    }

                    //creating adapter object and setting it to recyclerview
                    ContenidosAdapter adapter = new ContenidosAdapter(VideoContenido.this, contenidoList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VideoContenido.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}