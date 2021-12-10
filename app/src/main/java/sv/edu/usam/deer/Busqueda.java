package sv.edu.usam.deer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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

import sv.edu.usam.deer.Adapters.ContenidosAdapter;
import sv.edu.usam.deer.Adapters.ListaSimpleAlbumAdapter;
import sv.edu.usam.deer.Models.Contenido;

public class Busqueda extends AppCompatActivity {

    private String titulo = "";
    private String URL_ALBUM = "";

    private TextView txtSearch;
    ImageView btnSearch;
    List<Contenido> contenidoList;
    private RequestQueue requestQueue;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        txtSearch = findViewById(R.id.edtSearchInput);
        btnSearch = findViewById(R.id.imbSearch);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.rvResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //initializing the list
        contenidoList = new ArrayList<>();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titulo = txtSearch.getText().toString();
                if (txtSearch.getText().toString() == null){
                    Toast.makeText(getApplicationContext(), "No se puede buscar vacios", Toast.LENGTH_LONG).show();
                } else {
                    contenidoList.clear();
                    loadContenidos(titulo);
                }
            }
        });

    }

    private void loadContenidos(String titulo) {
        titulo.replace(" ","%20");
        URL_ALBUM = "https://deervideo2021.000webhostapp.com/Deer_API/api/search.php?search="+ titulo;
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
                    ContenidosAdapter adapter = new ContenidosAdapter(Busqueda.this, contenidoList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Busqueda.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}