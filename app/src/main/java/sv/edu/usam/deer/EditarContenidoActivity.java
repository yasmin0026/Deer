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

public class EditarContenidoActivity extends AppCompatActivity {
    EditText Cancion,artistas, albums,fechas,link,portadas;
    TextView id;
    Spinner generos;
    Button btnEdit;

    //Calendario datepicker
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar c = Calendar.getInstance();


    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contenido);

        //Calendar datepicker
        sMonthIni = c.get(Calendar.MONTH);
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);

        id = findViewById(R.id.edtid);
        Cancion = findViewById(R.id.edtnombres);
        artistas = findViewById(R.id.edtApellidos);
        albums = findViewById(R.id.edtUsuariouseradd);
        generos = findViewById(R.id.edtGenero2);
        fechas = findViewById(R.id.edtClave);
        link = findViewById(R.id.edtId_rol);
        portadas = findViewById(R.id.edtPortada2);
        btnEdit = findViewById(R.id.btnAgregarUser);

        //Back Arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        id.setText(Contenido_AdminActivity.contList.get(position).getId_contenido());
        Cancion.setText(Contenido_AdminActivity.contList.get(position).getNombre_cancion());
        artistas.setText(Contenido_AdminActivity.contList.get(position).getArtista());
        albums.setText(Contenido_AdminActivity.contList.get(position).getAlbum());
        generos.getSelectedItem().toString().trim();
        fechas.setText(Contenido_AdminActivity.contList.get(position).getFecha_lanzamiento());
        link.setText(Contenido_AdminActivity.contList.get(position).getLink_video());
        portadas.setText(Contenido_AdminActivity.contList.get(position).getPortada());

        fechas.setOnClickListener((view -> {
            showDialog(DATE_ID);
        }));
    }

    public void btn_edit(View view){

        final String id_contenido = id.getText().toString().trim();
        final String nombre_cancion = Cancion.getText().toString().trim();
        final String artista = artistas.getText().toString().trim();
        final String album = albums.getText().toString().trim();
        final String genero = generos.getSelectedItem().toString().trim();
        final String fecha_lanzamiento = fechas.getText().toString().trim();
        final String  link_video = link.getText().toString().trim();
        final String portada = portadas.getText().toString().trim();





        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://deervideo2021.000webhostapp.com/crud/editar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(EditarContenidoActivity.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Contenido_AdminActivity.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(EditarContenidoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();

                params.put("id_contenido",id_contenido);
                params.put("nombre_cancion",nombre_cancion);
                params.put("artista",artista);
                params.put("album",album);
                params.put("genero",genero);
                params.put("fecha_lanzamiento",fecha_lanzamiento);
                params.put("link_video",link_video);
                params.put("portada",portada);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(EditarContenidoActivity.this);
        requestQueue.add(request);

    }

    private void fecha(){
        fechas.setText((nMonthIni +1)+"/"+ nDayIni+"/"+ nYearIni+" ");
    }

    private DatePickerDialog.OnDateSetListener DatesetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthofyear, int dayOfmonth) {
            nYearIni = year;
            nMonthIni = monthofyear;
            nDayIni = dayOfmonth;

            fecha();
        }
    };

    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_ID:
                return new DatePickerDialog(this, DatesetListener, sYearIni,sMonthIni,sDayIni);
        }

        return null;
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