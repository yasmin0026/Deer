package sv.edu.usam.deer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sv.edu.usam.deer.ui.home.HomeFragment;

public class IniciarSesion extends AppCompatActivity {
    EditText usuario,password, id_rol;
    Button btnLogin;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        usuario = findViewById(R.id.edtUsuario);
        password = findViewById(R.id.edtPass);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loguearUser("https://deervideo2021.000webhostapp.com/crud/login.php?usuario="+usuario.getText().toString()+"&clave="+password.getText().toString());
            }
        });
        /*---------------------------------------------------------------*/
    }


    public void loguearUser(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        usuario.setText(jsonObject.getString("usuario"));
                        password.setText(jsonObject.getString("clave"));
                        id = Integer.parseInt(jsonObject.getString("id_rol"));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
                    }
                }
                Intent intent = new Intent(getApplicationContext(), MenuNavegacion.class);
                intent.putExtra("id_rol", id);
                startActivity(intent);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    /*-----------------------------------------------------------------*/

}