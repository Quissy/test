package space_funexp.github.com.funex;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Process;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationService extends Service implements LocationListener {
    private ExecutorService mThreadPool = Executors.newSingleThreadExecutor();

    @Override
    public void onCreate() {
        Log.i("FunEx", "location service created");
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER ) ) {
            //show no notification
            Log.i("FunEx", "no network enabled");
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);
        } catch (SecurityException e) {
            //show no notification
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void onProviderEnabled(String provider) {

    }

    public void onProviderDisabled(String provider) {

    }

    public void onLocationChanged(final Location location) {
        // Called when a new location is found by the network location provider.
        //makeUseOfNewLocation(location);

        //if location is near geopositions, launch view
        //NotificationManager.notify()

        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                // Perform your long-running tasks here.
                //...
                Log.i("FunEx: LocationService", location.toString());
                showNotification(location);
            }
        });
    }

    public void showNotification(Location location) {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("ticker")
                .setSmallIcon(R.drawable.ic_stat_significant_event)
                .setContentTitle("title")
                .setContentText("context: " + location.toString())
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
