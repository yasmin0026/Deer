package sv.edu.usam.deer.ui.artistas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sv.edu.usam.deer.Adapters.ArtistasAdapter;
import sv.edu.usam.deer.Models.Artista;
import sv.edu.usam.deer.R;

public class Artistas_Fragment extends Fragment {

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL = "https://deervideo2021.000webhostapp.com/Deer_API/api/get_AllArtistas.php";

    //a list to store all the products
    List<Artista> contenidoList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_artistas, container, false);
        //getting the recyclerview from xml
        recyclerView = root.findViewById(R.id.rvAllArtistas);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Auto adaptable table
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        //settear
        recyclerView.setLayoutManager(layoutManager);
        //initializing the list
        contenidoList = new ArrayList<>();
        //this method will fetch and parse json
        //to display it in recyclerview
        loadContenidos();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    private void loadContenidos() {
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
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
                        contenidoList.add(new Artista(
                                contenido.getString("artista"),
                                contenido.getString("portada")
                        ));
                    }

                    //creating adapter object and setting it to recyclerview
                    ArtistasAdapter adapter = new ArtistasAdapter(getActivity(), contenidoList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}