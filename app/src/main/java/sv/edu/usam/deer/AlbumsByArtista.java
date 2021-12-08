package sv.edu.usam.deer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

import sv.edu.usam.deer.Adapters.ContenidoAlbumnesAdapter;
import sv.edu.usam.deer.Models.AlbumsArt;

public class AlbumsByArtista extends AppCompatActivity {

    private RequestQueue requestQueue;
    private String artistaName = "";
    private String URL_r = "";
    private TextView txtNombre;
    private ImageView imFoto;

    //a list to store all the products
    List<AlbumsArt> contenidoList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums_by_artista);

        txtNombre = findViewById(R.id.artistaName);
        imFoto = findViewById(R.id.artistaIm);

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                artistaName = null;
            } else {
                artistaName = extra.getString("artista");
            }
        } else {
            artistaName = (String) savedInstanceState.getSerializable("artista");
        }

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.rvAbumsA);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Auto adaptable table
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        //settear
        recyclerView.setLayoutManager(layoutManager);

        //initializing the list
        contenidoList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        artistaName.replace(" ","%20");

        loadContenidos(artistaName);
        loadDatosArtista("https://deervideo2021.000webhostapp.com/Deer_API/api/get_artista.php?artist=" + artistaName);
    }

    private void loadDatosArtista(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for(int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        txtNombre.setText(jsonObject.getString("artista"));
                        //loading the image
                        Glide.with(AlbumsByArtista.this)
                                .load(jsonObject.getString("portada"))
                                .into(imFoto);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
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

    private void loadContenidos(String artistaN) {
        URL_r = "https://deervideo2021.000webhostapp.com/Deer_API/api/get_albumByArtist.php?artist=" + artistaN;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_r, new Response.Listener<String>() {
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
                        contenidoList.add(new AlbumsArt(
                                contenido.getString("artista"),
                                contenido.getString("album"),
                                contenido.getString("portada")
                        ));
                    }

                    //creating adapter object and setting it to recyclerview
                    ContenidoAlbumnesAdapter adapter = new ContenidoAlbumnesAdapter(AlbumsByArtista.this, contenidoList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AlbumsByArtista.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}