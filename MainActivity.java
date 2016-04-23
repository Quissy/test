package space_funexp.github.com.funex;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("FunEx", "main started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("FunEx", "sending start service intent");
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }
}
