package sv.edu.usam.deer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleContenidoActivity extends AppCompatActivity {
    TextView txtid, txtnomCancion, txtartistas, txtalbums, txtGen,txtFecha_lanzamiento,txtLink_video,txtPortadas;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contenido);

        txtid = findViewById(R.id.txtid_contenido);
        txtnomCancion = findViewById(R.id.txtCancion);
        txtartistas = findViewById(R.id.txtArtistas);
        txtalbums = findViewById(R.id.txtAlbums);
        txtGen = findViewById(R.id.txtGen);
        txtFecha_lanzamiento = findViewById(R.id.txtFecha_lanzamiento);
        txtLink_video = findViewById(R.id.txtLink_video);
        txtPortadas = findViewById(R.id.txtPortadas);

        Intent intent =getIntent();
        position = intent.getExtras().getInt("position");


        txtid.setText("ID: "+Contenido_AdminActivity.contList.get(position).getId_contenido());
        txtnomCancion.setText("Cancion: "+Contenido_AdminActivity.contList.get(position).getNombre_cancion());
        txtartistas.setText("Artista: "+Contenido_AdminActivity.contList.get(position).getArtista());
        txtalbums.setText("Album: "+Contenido_AdminActivity.contList.get(position).getAlbum());
        txtGen.setText("Genero: "+Contenido_AdminActivity.contList.get(position).getGenero());
        txtFecha_lanzamiento.setText("Fecha: "+Contenido_AdminActivity.contList.get(position).getFecha_lanzamiento());
        txtLink_video.setText("Link video: "+Contenido_AdminActivity.contList.get(position).getLink_video());
        txtPortadas.setText("Portada: "+Contenido_AdminActivity.contList.get(position).getPortada());
    }
}