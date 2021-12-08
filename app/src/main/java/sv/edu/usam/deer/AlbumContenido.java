package sv.edu.usam.deer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sv.edu.usam.deer.Adapters.ListaSimpleAlbumAdapter;
import sv.edu.usam.deer.Models.Contenido;

public class AlbumContenido extends AppCompatActivity {

    private String artistaName = "";
    private String albumName = "";

    private TextView txtArtista,txtAlbum,txtFecha;
    ImageView albumCover;

    private String URL_ALBUM = "";
    List<Contenido> contenidoList;
    private RequestQueue requestQueue;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_contenido);

        txtArtista = findViewById(R.id.artistaLabel);
        txtAlbum = findViewById(R.id.albumLabel);
        txtFecha = findViewById(R.id.dateLabel);
        albumCover = findViewById(R.id.albumCover);

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                artistaName = null;
                albumName = null;
            } else {
                artistaName = extra.getString("artistaName");
                albumName = extra.getString("albumName");
            }
        } else {
            artistaName = (String) savedInstanceState.getSerializable("artistaName");
            albumName = (String) savedInstanceState.getSerializable("albumName");
        }

        artistaName.replace(" ","%20");
        albumName.replace(" ","%20");
        loadDatos("https://deervideo2021.000webhostapp.com/Deer_API/api/playerlist.php?artist="+ artistaName +"&album="+ albumName);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.rvListaCanciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //initializing the list
        contenidoList = new ArrayList<>();

        loadContenidos(artistaName,albumName);
    }

    private void loadDatos(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for(int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        Glide.with(AlbumContenido.this)
                                .load(jsonObject.getString("portada"))
                                .into(albumCover);
                        txtArtista.setText(jsonObject.getString("artista"));
                        txtAlbum.setText(jsonObject.getString("album"));
                        txtFecha.setText(jsonObject.getString("fecha_lanzamiento"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    txtArtista.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(txtArtista.getText().toString() == "Loanding....") {
                                Toast.makeText(getApplicationContext(), "Espere...", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(AlbumContenido.this, AlbumsByArtista.class);
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
                    ListaSimpleAlbumAdapter adapter = new ListaSimpleAlbumAdapter(AlbumContenido.this, contenidoList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AlbumContenido.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}