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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sv.edu.usam.deer.Adapters.ContenidoAlbumnesAdapter;
import sv.edu.usam.deer.Models.AlbumsArt;
import sv.edu.usam.deer.Models.CancionGenero;

public class CancionByGeneroActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    List<CancionGenero> contenidoList;
    private RequestQueue requestQueue;
    private String genero = "";
    private String URL_r = "";
    private TextView txtNombre;
    private ImageView imFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancion_by_genero);

        recyclerView = findViewById(R.id.Listcancionxartista);

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                genero = null;
            } else {
                genero = extra.getString("genero");
            }
        } else {
            genero = (String) savedInstanceState.getSerializable("genero");
        }

        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        //settear
        recyclerView.setLayoutManager(layoutManager);


        contenidoList = new ArrayList<>();

    }

  /*  private void loadContenidos(String genero) {
        URL_r = "https://deervideo2021.000webhostapp.com/Deer_API/api/get_albumByArtist.php?artist=" + genero;

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
                        contenidoList.add(new CancionGenero(
                                contenido.getString("genero"),
                                contenido.getString("album"),
                                contenido.getString("portada")
                        ));
                    }

                    //creating adapter object and setting it to recyclerview
                    ContenidoAlbumnesAdapter adapter = new ContenidoAlbumnesAdapter(CancionByGeneroActivity.this, contenidoList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CancionByGeneroActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }*/
}