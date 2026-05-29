package com.example.myapplication1;

import android.app.*;
import android.content.*;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import java.io.FileOutputStream;

// Service in background per log su file e notifiche
public class BookingService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String info = intent.getStringExtra("FLIGHT_INFO");
            String dest = intent.getStringExtra("DEST");

            // Scrittura della conferma su file locale
            try (FileOutputStream fos = openFileOutput("booking_log.txt", MODE_APPEND)) {
                fos.write(("Confermato: " + info + "\n").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Invio notifica di successo
            sendNotif(info, dest);
        }
        stopSelf(); // Il service si chiude dopo l'operazione
        return START_NOT_STICKY;
    }

    // Creazione e invio della notifica di sistema
    private void sendNotif(String info, String dest) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        
        // Configurazione canale per Android O e superiori
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) 
            nm.createNotificationChannel(new NotificationChannel("ch1", "Notifiche", NotificationManager.IMPORTANCE_DEFAULT));
        
        // Intent per tornare all'app cliccando la notifica
        PendingIntent pi = PendingIntent.getActivity(this, 0, 
            new Intent(this, BookingActivity.class).putExtra("FLIGHT_INFO", info).putExtra("DEST", dest), 
            PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        // Costruzione e invio notifica
        nm.notify(1, new NotificationCompat.Builder(this, "ch1")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Volo Confermato")
                .setContentText("Prenotazione per " + dest + " riuscita")
                .setContentIntent(pi).setAutoCancel(true).build());
    }

    @Override public IBinder onBind(Intent intent) { return null; }
}
