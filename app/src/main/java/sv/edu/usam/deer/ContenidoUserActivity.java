package sv.edu.usam.deer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import sv.edu.usam.deer.Adapters.AdaptadorUser;
import sv.edu.usam.deer.Models.Contenido;
import sv.edu.usam.deer.Models.ContenidoUser;

public class ContenidoUserActivity extends AppCompatActivity {

    
    ListView listView;
    AdaptadorUser adapter;
    public static ArrayList<ContenidoUser> contList = new ArrayList<>();

    String url = "https://deervideo2021.000webhostapp.com/crud/mostrarUser.php";
    ContenidoUser contenido;
    private SearchView searchViewBuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido_user);

        listView = findViewById(R.id.myListView);
        adapter = new AdaptadorUser(this, contList);
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
                builder.setTitle(contList.get(position).getNombres());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        switch (i){

                            case 0:

                                startActivity(new Intent(getApplicationContext(), DetalleUser.class)
                                        .putExtra("position",position));

                                break;

                            case 1:
                                startActivity(new Intent(getApplicationContext(), EditarUser.class)
                                        .putExtra("position",position));

                                break;

                            case 2:

                                deleteData(contList.get(position).getId());

                                break;


                        }



                    }
                });


                builder.create().show();


            }
        }); //fin de metodo


    }

    private void deleteData(final String id) {


        StringRequest request = new StringRequest(Request.Method.POST, "https://deervideo2021.000webhostapp.com/crud/eliminarUser.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("datos eliminados")){
                            Toast.makeText(ContenidoUserActivity.this, "Datos eliminados correctamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ContenidoUserActivity.class));
                        }
                        else{
                            Toast.makeText(ContenidoUserActivity.this, "Datos no eliminados", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ContenidoUserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();
                params.put("id", id);
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
                            JSONArray jsonArray = jsonObject.getJSONArray("usuario");

                            if(sucess.equals("1")){


                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String nombres = object.getString("nombres");
                                    String apellidos = object.getString("apellidos");
                                    String usuario = object.getString("usuario");
                                    String clave = object.getString("clave");
                                    String id_rol = object.getString("id_rol");

                                    contenido = new ContenidoUser(id,nombres,apellidos,usuario,clave,id_rol);
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
                Toast.makeText(ContenidoUserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);




    }//fin mostrar


    public void btn_add_activity(View view){
        startActivity(new Intent(getApplicationContext(),AgregarUser.class));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}