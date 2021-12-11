package sv.edu.usam.deer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Acercade extends AppCompatActivity {

    WebView vista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);

        vista = findViewById(R.id.infor_app);
        vista.loadUrl("https://deervideo2021.000webhostapp.com/Deer_API/acercade/acercade.php");
        WebSettings webSettings = vista.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}