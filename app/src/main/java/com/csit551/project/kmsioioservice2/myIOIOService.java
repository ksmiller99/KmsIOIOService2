package com.csit551.project.kmsioioservice2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOService;

/**
 * Created by Kevin on 2/26/2016.
 */
public class myIOIOService extends IOIOService {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected IOIOLooper createIOIOLooper() {
        return new BaseIOIOLooper() {
            private DigitalOutput led_;

            /*--- this pin indicates to the ioio monitor processor that we are alive -----*/
            private DigitalOutput alive_pin;
            //private Uart uart_;
            //private InputStream reading_;


            @Override
            protected void setup() throws ConnectionLostException,
                    InterruptedException {
                led_ = ioio_.openDigitalOutput(IOIO.LED_PIN);
            }

            @Override
            public void loop() throws ConnectionLostException,
                    InterruptedException {
//                led_.write(false);
//                Thread.sleep(500);
//                led_.write(true);
//                Thread.sleep(500);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Service onStartCommand()", Toast.LENGTH_SHORT).show();
        int result = super.onStartCommand(intent, flags, startId);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (intent != null && intent.getAction() != null
                && intent.getAction().equals("stop")) {
            // User clicked the notification. Need to stop the service.
            nm.cancel(0);
            Toast.makeText(getApplicationContext(), "Service Stopped onStartCommand()", Toast.LENGTH_SHORT).show();
            stopSelf();
        } else {
            // Service starting. Create a notification.
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            Notification notification = builder.setContentIntent(PendingIntent.getService(this, 0, new Intent("stop", null, this, this.getClass()), 0))
                    .setSmallIcon(R.mipmap.ic_launcher).setTicker("KSM2 IOIOService running").setWhen(System.currentTimeMillis())
                    .setAutoCancel(true).setContentTitle("KSM2 IOIOService")
                    .setContentText("KSM2 IOIOService running").build();
            nm.notify(0, notification);
            Toast.makeText(getApplicationContext(), "Service Starting onStartCommand()", Toast.LENGTH_SHORT).show();

        }
        return result;
    }

}
