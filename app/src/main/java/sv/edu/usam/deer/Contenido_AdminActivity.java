package sv.edu.usam.deer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

import sv.edu.usam.deer.Adapters.Adaptador;
import sv.edu.usam.deer.Models.Contenido;

public class Contenido_AdminActivity extends AppCompatActivity {
    ListView listView;
    Adaptador adapter;
    public static ArrayList<Contenido> contList = new ArrayList<>();

    String url = "https://deervideo2021.000webhostapp.com/crud/mostrar.php";
    Contenido contenido;
    private SearchView searchViewBuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido_admin);

        listView = findViewById(R.id.myListView);
        adapter = new Adaptador(this, contList);
        listView.setAdapter(adapter);
        searchViewBuscar=findViewById(R.id.buscar);

        retrieveData();



        //muestra las opciones(ver detalles, editar y eliminar)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                CharSequence[] dialogItem = {"Mostrar datos","Editar datos","Eliminar"};
                builder.setTitle(contList.get(position).getNombre_cancion());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        switch (i){

                            case 0:

                                startActivity(new Intent(getApplicationContext(), DetalleContenidoActivity.class)
                                        .putExtra("position",position));

                                break;

                            case 1:
                                startActivity(new Intent(getApplicationContext(), EditarContenidoActivity.class)
                                        .putExtra("position",position));

                                break;

                            case 2:

                                deleteData(contList.get(position).getId_contenido());

                                break;


                        }



                    }
                });


                builder.create().show();


            }
        }); //fin de metodo


    }

    private void deleteData(final String id_contenido) {


        StringRequest request = new StringRequest(Request.Method.POST, "https://deervideo2021.000webhostapp.com/crud/eliminar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("datos eliminados")){
                            Toast.makeText(Contenido_AdminActivity.this, "Datos eliminados correctamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Contenido_AdminActivity.class));
                        }
                        else{
                            Toast.makeText(Contenido_AdminActivity.this, "Datos no eliminados", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Contenido_AdminActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();
                params.put("id_contenido", id_contenido);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    //mostrar
    public void retrieveData(){

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        contList.clear();
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("contenido");

                            if(sucess.equals("1")){


                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id_contenido = object.getString("id_contenido");
                                    String nombre_cancion = object.getString("nombre_cancion");
                                    String artista = object.getString("artista");
                                    String album = object.getString("album");
                                    String genero = object.getString("genero");
                                    String fecha_lanzamiento = object.getString("fecha_lanzamiento");
                                    String link_video = object.getString("link_video");
                                    String 	portada = object.getString("portada");

                                    contenido = new Contenido(id_contenido,nombre_cancion,artista,album,genero,fecha_lanzamiento,link_video,portada);
                                    contList.add(contenido);
                                    adapter.notifyDataSetChanged();



                                }


                            }




                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }






                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Contenido_AdminActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);




    }//fin mostrar


    public void btn_add_activity(View view){
        startActivity(new Intent(getApplicationContext(),AgregarContenidoActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}