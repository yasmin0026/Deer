package sv.edu.usam.deer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import sv.edu.usam.deer.Models.ContenidoUser;

public class AgregarUser extends AppCompatActivity {
    EditText Nombres, Apellidos, Usuario, Clave, Id_rol;
    Button btn_insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_user);


        Nombres = findViewById(R.id.edtnombres);
        Apellidos    = findViewById(R.id.edtApellidos);
        Usuario  = findViewById(R.id.edtUsuariouseradd);
        Clave  = findViewById(R.id.edtClave);
        Id_rol  = findViewById(R.id.edtId_rol);
        btn_insert = findViewById(R.id.btnAgregarUser);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

        //Back Arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void insertData() {
        final String nombre = Nombres.getText().toString().trim();
        final String apellido = Apellidos.getText().toString().trim();
        final String usuario = Usuario.getText().toString().trim();
        final String clave = Clave.getText().toString().trim();
        final String rol = Id_rol.getText().toString().trim();


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cargando...");

        if(nombre.isEmpty()){


            Nombres.setError("complete los campos");
            return;
        }
        else if(apellido.isEmpty()){

            Apellidos.setError("complete los campos");
            return;
        }
        else if(usuario.isEmpty()){
            Usuario.setError("complete los campos");
            return;
        }
        else if(clave.isEmpty()){
            Clave.setError("complete los campos");
            return;
        }
        else if(rol.isEmpty()){
            Id_rol.setError("complete los campos");
            return;
        }

        else{
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "https://deervideo2021.000webhostapp.com/crud/insertarUser.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equalsIgnoreCase("datos insertados")){
                                limpiar();
                                Toast.makeText(AgregarUser.this, "Datos insertados", Toast.LENGTH_SHORT).show();

                                progressDialog.dismiss();
                            }
                            else{
                                Toast.makeText(AgregarUser.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AgregarUser.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();

                    params.put("nombres",nombre);
                    params.put("apellidos",apellido);
                    params.put("usuario",usuario);
                    params.put("clave",clave);
                    params.put("id_rol",rol);

                    return params;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(AgregarUser.this);
            requestQueue.add(request);



        }

    }

    private void limpiar() {

        Intent intent=new Intent(AgregarUser.this, ContenidoUserActivity.class);
        startActivity(intent);
        Nombres.setText("");
        Apellidos.setText("");
        Usuario.setText("");
        Clave.setText("");
        Id_rol.setText("");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}