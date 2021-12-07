package sv.edu.usam.deer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class AgregarContenidoActivity extends AppCompatActivity {
    EditText edtCancion, edtArtista, edtAlbum, edtFecha, edtLink, edtPortada;
    Spinner edtGenero;
    Button btn_insert;

    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contenido);
        //Datepicker
        sMonthIni = c.get(Calendar.MONTH);
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);

        edtCancion = findViewById(R.id.edtcancion);
        edtArtista    = findViewById(R.id.edtArtista);
        edtAlbum  = findViewById(R.id.edtAlbum);
        edtGenero  = findViewById(R.id.edtGenero);
        edtFecha  = findViewById(R.id.edtFecha);
        btn_insert = findViewById(R.id.btnActualizar);
        edtLink = findViewById(R.id.edtLink);
        edtPortada = findViewById(R.id.edtPortada);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

        edtFecha.setOnClickListener(view -> {
            showDialog(DATE_ID);
        });
    }

    private void insertData() {
        final String nombre_cancion = edtCancion.getText().toString().trim();
        final String artista = edtArtista.getText().toString().trim();
        final String album = edtAlbum.getText().toString().trim();
        final String genero = edtGenero.getSelectedItem().toString().trim();
        final String fecha_lanzamiento = edtFecha.getText().toString().trim();
        final String  link_video = edtLink.getText().toString().trim();
        final String portada = edtPortada.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cargando...");

        if(nombre_cancion.isEmpty()){


            edtCancion.setError("complete los campos");
            return;
        }
        else if(artista.isEmpty()){

            edtArtista.setError("complete los campos");
            return;
        }
        else if(album.isEmpty()){
            edtAlbum.setError("complete los campos");
            return;
        }
        else if(link_video.isEmpty()){
            edtLink.setError("complete los campos");
            return;
        }

        else{
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "https://deervideo2021.000webhostapp.com/crud/insertar.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equalsIgnoreCase("datos insertados")){
                                limpiar();
                                Toast.makeText(AgregarContenidoActivity.this, "Datos insertados", Toast.LENGTH_SHORT).show();

                                progressDialog.dismiss();
                            }
                            else{
                                Toast.makeText(AgregarContenidoActivity.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AgregarContenidoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();

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


            RequestQueue requestQueue = Volley.newRequestQueue(AgregarContenidoActivity.this);
            requestQueue.add(request);



        }

    }

    private void limpiar() {

        Intent intent=new Intent(AgregarContenidoActivity.this,Contenido_AdminActivity.class);
        startActivity(intent);
        edtCancion.setText("");
        edtArtista.setText("");
        edtAlbum.setText("");
        edtFecha.setText("");
        edtLink.setText("");
        edtPortada.setText("");
    }

    private void fecha(){
        edtFecha.setText((nMonthIni +1)+"/"+ nDayIni+"/"+ nYearIni+" ");
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

   @Override
   public void onBackPressed() { moveTaskToBack(true); }
}