package sv.edu.usam.deer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import sv.edu.usam.deer.databinding.ActivityMenuBinding;

public class MenuNavegacion extends AppCompatActivity {

    String id;

    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_generos, R.id.navigation_artistas)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

      /*  if(savedInstanceState == null){
            Bundle extra = getIntent().getExtras();
            if(extra == null){
                Toast.makeText(this, "Sin rol",  Toast.LENGTH_LONG).show();
            }else{
                id = extra.getString("id_rol");
            }

        }else {
            id = (String) savedInstanceState.getSerializable("id_rol");
        }*/

/*----------------------------------------------------------------*/
    }

    //Menu de opciones (no es el Navegation Bottom)
    public boolean onCreateOptionsMenu(Menu menu){

       /* if(id.equals("1")){
            getMenuInflater().inflate(R.menu.opciones_admin, menu);
            return true;
        }else if(id.equals("2")){
            getMenuInflater().inflate(R.menu.opciones_usuarios, menu);
            return true;

        }*/

        getMenuInflater().inflate(R.menu.opciones, menu);
        return true;

    }

    //crear las opciones cada propiedad del menu
    public boolean onOptionsItemSelected(MenuItem item){
        //
        int idMenu = item.getItemId();
        switch(idMenu){
            case R.id.iniciar_sesion:
                viewSingIn();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        //return super.onOptionsItemSelected(item);
    }

    public void viewSingIn(){
        Intent intent = new Intent(getApplicationContext(), IniciarSesion.class);
        startActivity(intent);
    }


/*------------------------------------------------------------------------------------*/
}