package com.example.myapplication1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Activity per la gestione della prenotazione e azioni correlate
public class BookingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Recupero dettagli volo
        final String info = getIntent().getStringExtra("FLIGHT_INFO");
        final String dest = getIntent().getStringExtra("DEST");

        // Visualizzazione dettagli
        ((TextView)findViewById(R.id.tv_booking_details)).setText("Volo: " + info + "\nDest: " + dest);

        // Bottone Conferma: avvia il Service di registrazione
        findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            Intent s = new Intent(this, BookingService.class);
            s.putExtra("FLIGHT_INFO", info);
            s.putExtra("DEST", dest);
            startService(s);
            Toast.makeText(this, "Prenotazione avviata", Toast.LENGTH_SHORT).show();
        });

        // Bottone Mappa: apre l'aeroporto di destinazione
        findViewById(R.id.btn_map).setOnClickListener(v -> 
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Airport+" + dest))));

        // Bottone Condividi: invia testo tramite app esterne
        findViewById(R.id.btn_share).setOnClickListener(v -> {
            Intent sh = new Intent(Intent.ACTION_SEND).setType("text/plain");
            sh.putExtra(Intent.EXTRA_TEXT, "Ho prenotato per " + dest);
            startActivity(Intent.createChooser(sh, "Condividi"));
        });

        // Bottone Home: torna alla schermata iniziale
        findViewById(R.id.btn_back_home).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        });
    }
}
