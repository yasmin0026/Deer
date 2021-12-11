package sv.edu.usam.deer.ui.generos;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import sv.edu.usam.deer.Adapters.ContenidosAdapter;
import sv.edu.usam.deer.Adapters.GeneroAdapter;
import sv.edu.usam.deer.Models.Contenido;
import sv.edu.usam.deer.R;

public class GenerosFragment extends Fragment {

    private static final String URL = "https://deervideo2021.000webhostapp.com/Deer_API/api/prueba.php";

    //a list to store all the products
    List<Contenido> genList;

    //the recyclerview
    RecyclerView recyclerView;

   // private GenerosViewModel mViewModel;

    /*
    public static GenerosFragment newInstance() {
        return new GenerosFragment();
    }*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_generos, container, false);

        recyclerView = root.findViewById(R.id.listaGeneros);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initializing the list
        genList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview

        mostrarGeneros();

        return root;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GenerosViewModel mGenerosModel = new ViewModelProvider(this).get(GenerosViewModel.class);
        // TODO: Use the ViewModel
    }

    private void mostrarGeneros() {

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
                        genList.add(new Contenido(
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
                    GeneroAdapter adapter = new GeneroAdapter(getActivity(), genList);
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