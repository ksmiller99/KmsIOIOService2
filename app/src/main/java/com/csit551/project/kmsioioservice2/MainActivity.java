package com.csit551.project.kmsioioservice2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    /** Messenger for communicating with the service. */
    Messenger mService = null;

    /** Flag indicating whether we have called bind on the service. */
    boolean mBound;

    SeekBar seekBar;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, myIOIOService.class));
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);

        ((TextView) findViewById(R.id.textView2)).setText("" + ((SeekBar) findViewById(R.id.seekBar)).getProgress());


    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Toast.makeText(getApplicationContext(), "MainActivity.ServiceConnection.onServiceConnected()", Toast.LENGTH_LONG).show();
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            mService = new Messenger(service);
            mBound = true;
            Log.d("KSM", "onServiceConnected");
        }

        public void onServiceDisconnected(ComponentName className) {
            Toast.makeText(getApplicationContext(), "MainActivity.ServiceConnection.onServiceDisconnect()", Toast.LENGTH_LONG).show();
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mBound = false;
            Log.d("KSM", "onServiceDisconnected");
        }
    };


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        TextView tv = (TextView)findViewById(R.id.textView2);
        tv.setText("" + seekBar.getProgress());

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
