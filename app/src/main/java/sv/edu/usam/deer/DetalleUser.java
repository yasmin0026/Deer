package sv.edu.usam.deer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleUser extends AppCompatActivity {
    TextView txtid, txtnombres, txtapellidos, txtusuario, txtclave,txtid_rol;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_user);

        txtid = findViewById(R.id.txtid);
        txtnombres = findViewById(R.id.txtNombres);
        txtapellidos = findViewById(R.id.txtApellidos);
        txtusuario = findViewById(R.id.txtUserNick);
        txtclave = findViewById(R.id.txtClave);
        txtid_rol = findViewById(R.id.txtId_rol);

        Intent intent =getIntent();
        position = intent.getExtras().getInt("position");


        txtid.setText("ID: "+ContenidoUserActivity.contList.get(position).getId());
        txtnombres.setText("Nombres: "+ContenidoUserActivity.contList.get(position).getNombres());
        txtapellidos.setText("Apellidos: "+ContenidoUserActivity.contList.get(position).getApellidos());
        txtusuario.setText("Usuario: "+ContenidoUserActivity.contList.get(position).getUsuario());
        txtclave.setText("Clave: "+ContenidoUserActivity.contList.get(position).getClave());
        txtid_rol.setText("Rol: "+ContenidoUserActivity.contList.get(position).getId_rol());

    }
}