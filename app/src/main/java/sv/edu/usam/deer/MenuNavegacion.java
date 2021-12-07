package sv.edu.usam.deer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import sv.edu.usam.deer.databinding.ActivityMenuBinding;

public class MenuNavegacion extends AppCompatActivity {

    int id;

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

        if(savedInstanceState == null){
            Bundle extra = getIntent().getExtras();
            if(!(extra == null)){
                id = extra.getInt("id_rol");
            }

        }else {
            id = (int) savedInstanceState.getSerializable("id_rol");
        }

/*----------------------------------------------------------------*/
    }

    //Menu de opciones (no es el Navegation Bottom)
    public boolean onCreateOptionsMenu(Menu menu){

       if(id == 1){
            getMenuInflater().inflate(R.menu.opciones_admin, menu);
            return true;
        }else if(id == 2 ){
            getMenuInflater().inflate(R.menu.opciones_usuarios, menu);
            return true;
        }else {
           getMenuInflater().inflate(R.menu.opciones, menu);
           return true;
       }
    }

    //crear las opciones cada propiedad del menu
    public boolean onOptionsItemSelected(MenuItem item){
        //
        int idMenu = item.getItemId();
        switch(idMenu){

            case R.id.iniciar_sesion:
                viewSingIn();
                return true;

            case R.id.cerrar_sesion:
                logOut();

            default:
                return super.onOptionsItemSelected(item);

            case R.id.admin_contenido:
                contenido();
                return true;

            case R.id.admin_usuarios:
                contenidoUser();
                return true;
        }

        //return super.onOptionsItemSelected(item);
    }



    public void viewSingIn(){
        Intent intent = new Intent(getApplicationContext(), IniciarSesion.class);
        startActivity(intent);
    }

    public void logOut(){
        Intent intent = new Intent(getApplicationContext(), MenuNavegacion.class);
        startActivity(intent);

    }

    public void contenido(){
        startActivity(new Intent(getApplicationContext(), Contenido_AdminActivity.class));
    }

    public void contenidoUser(){
        startActivity(new Intent(getApplicationContext(), ContenidoUserActivity.class));
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }


    /*------------------------------------------------------------------------------------*/

}