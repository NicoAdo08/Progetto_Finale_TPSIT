package com.example.myapplication1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.List;

// Activity principale per l'inserimento dati
public class MainActivity extends AppCompatActivity {
    private EditText etUsername, etDeparture, etDestination;
    private SharedPreferences sharedPreferences;
    
    // Lista dei capoluoghi per la validazione
    private final List<String> capoluoghiEuropei = Arrays.asList(
            "Roma", "Parigi", "Berlino", "Madrid", "Londra", "Lisbona", 
            "Amsterdam", "Bruxelles", "Vienna", "Praga", "Varsavia", 
            "Atene", "Budapest", "Stoccolma", "Oslo", "Copenaghen", "Dublino"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inizializzazione UI
        etUsername = findViewById(R.id.et_username);
        etDeparture = findViewById(R.id.et_departure);
        etDestination = findViewById(R.id.et_destination);
        Button btnSearch = findViewById(R.id.btn_search);

        // Recupero username salvato
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        etUsername.setText(sharedPreferences.getString("username", ""));

        // Gestione click tasto cerca
        btnSearch.setOnClickListener(v -> {
            String name = etUsername.getText().toString().trim();
            String dep = etDeparture.getText().toString().trim();
            String dest = etDestination.getText().toString().trim();

            // Controllo campi vuoti
            if (name.isEmpty() || dep.isEmpty() || dest.isEmpty()) {
                Toast.makeText(this, "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validazione destinazione
            if (!isCapoluogoEuropeo(dest)) {
                Toast.makeText(this, "Inserisci un capoluogo europeo valido", Toast.LENGTH_LONG).show();
                return;
            }

            // Salvataggio username e passaggio all'activity dei voli
            sharedPreferences.edit().putString("username", name).apply();
            Intent intent = new Intent(this, FlightListActivity.class);
            intent.putExtra("USER_NAME", name);
            intent.putExtra("DEP", dep);
            intent.putExtra("DEST", dest);
            startActivity(intent);
        });
    }

    // Verifica se la città è nella lista dei capoluoghi
    private boolean isCapoluogoEuropeo(String citta) {
        for (String c : capoluoghiEuropei) if (c.equalsIgnoreCase(citta)) return true;
        return false;
    }
}
