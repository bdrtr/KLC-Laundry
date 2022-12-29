package com.kilicarslan.KLC.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kilicarslan.KLC.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class pushNotService {

    private String token="";
    private Context cnt;
    public pushNotService(Context cnt) {
        this.cnt = cnt;
    }

    public void send() {

        //FirebaseMessaging.getInstance().send();
    }
    public void connect() {

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {

            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    token = task.getResult();

                    Log.i("token: ", token);
                }
            }
        });
    }


    public String getToken() {
        return this.token;
    }

    public void sendNotification(int body, int title) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1","1", NotificationManager.IMPORTANCE_HIGH);

            NotificationManager manager = this.cnt.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder b = new NotificationCompat.Builder(cnt,"1");
        b.setContentTitle(cnt.getString(title));
        b.setContentText(cnt.getString(body));
        b.setSmallIcon(R.drawable.mngpng);
        b.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(cnt);
        managerCompat.notify(1,b.build());
    }
}
