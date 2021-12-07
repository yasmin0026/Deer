package sv.edu.usam.deer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class EditarUser extends AppCompatActivity {
    EditText Nombres,Apellidos, Usuario,Clave,Rol;
    TextView id;
    Button btnEdit;

    //Calendario datepicker
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar c = Calendar.getInstance();


    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_user);

        //Calendar datepicker
        sMonthIni = c.get(Calendar.MONTH);
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);

        id = findViewById(R.id.edtid);
        Nombres = findViewById(R.id.edtnombres);
        Apellidos = findViewById(R.id.edtApellidos);
        Usuario = findViewById(R.id.edtUsuariouseradd);
        Clave = findViewById(R.id.edtClave);
        Rol = findViewById(R.id.edtId_rol);
        btnEdit = findViewById(R.id.btnAgregarUser);

        //Back Arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        id.setText(ContenidoUserActivity.contList.get(position).getId());
        Nombres.setText(ContenidoUserActivity.contList.get(position).getNombres());
        Apellidos.setText(ContenidoUserActivity.contList.get(position).getApellidos());
        Usuario.setText(ContenidoUserActivity.contList.get(position).getUsuario());
        Clave.setText(ContenidoUserActivity.contList.get(position).getClave());
        Rol.setText(ContenidoUserActivity.contList.get(position).getId_rol());

    }

    public void btn_edit(View view){

        final String iduser = id.getText().toString().trim();
        final String nombreuser = Nombres.getText().toString().trim();
        final String apellidouser = Apellidos.getText().toString().trim();
        final String usuariouser = Usuario.getText().toString().trim();
        final String claveuser = Clave.getText().toString().trim();
        final String  roluser = Rol.getText().toString().trim();





        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://deervideo2021.000webhostapp.com/crud/editarUser.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(EditarUser.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ContenidoUserActivity.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(EditarUser.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();

                params.put("id",iduser);
                params.put("nombres",nombreuser);
                params.put("apellidos",apellidouser);
                params.put("usuario",usuariouser);
                params.put("clave",claveuser);
                params.put("id_rol",roluser);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(EditarUser.this);
        requestQueue.add(request);

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