package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

// Activity che mostra l'elenco dei voli disponibili
public class FlightListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);

        // Riferimenti UI
        TextView tvWelcome = findViewById(R.id.tv_welcome);
        ListView lvFlights = findViewById(R.id.lv_flights);

        // Recupero dati dall'intent
        String name = getIntent().getStringExtra("USER_NAME");
        String dep = getIntent().getStringExtra("DEP");
        String dest = getIntent().getStringExtra("DEST");

        // Messaggio di benvenuto personalizzato
        tvWelcome.setText("Ciao " + name + ", voli da " + dep + " a " + dest);

        // Generazione dati fittizi per la lista
        ArrayList<String> flights = new ArrayList<>();
        flights.add("Volo AZ123 - 10:00 - 50€");
        flights.add("Volo FR456 - 14:30 - 30€");
        flights.add("Volo LH789 - 18:00 - 120€");

        // Configurazione adapter per la lista
        lvFlights.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, flights));

        // Gestione selezione volo
        lvFlights.setOnItemClickListener((p, v, pos, id) -> {
            Intent i = new Intent(this, BookingActivity.class);
            i.putExtra("FLIGHT_INFO", (String) p.getItemAtPosition(pos));
            i.putExtra("DEST", dest);
            startActivity(i);
        });
    }
}
