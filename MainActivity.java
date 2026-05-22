package com.example.flybooking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnFlights;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnFlights =
                findViewById(R.id.btnFlights);
        btnFlights.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            MainActivity.this,
                            FlightActivity.class
                    );
            startActivity(intent);
        });
    }
}
