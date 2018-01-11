package daempflok.googlemap;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class MainActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback{
    protected LocationManager lm;
    protected GoogleMap map;
    private LatLng[] posQ = new LatLng[5];
    private double currentLat;
    private double currentLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        addLocationListener();

        MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mf.getMapAsync(this);

        //BUTTONS
        Button saveButton=(Button)findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Database
                SQLiteDatabase db = openOrCreateDatabase("mysavedlocations.db", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS gps(ID INTEGER PRIMARY KEY AUTOINCREMENT, Latitude REAL, Longitude REAL);");

                ContentValues values;
                values = new ContentValues();
                values.put("Latitude", currentLat);
                values.put("Longitude", currentLng);

                db.insert("gps", null, values);
                Cursor c = db.rawQuery("SELECT * FROM gps",null);
                if (c.moveToFirst()){
                    do {
                        String output1 = c.getString(0);
                        String output2 = c.getString(1);
                        Log.i("GOTCHAAAH"," "+output1+" "+output2);
                    } while(c.moveToNext());
                }
                c.close();
                db.close();
                //Toast Msg
                Toast toast = Toast.makeText(getApplicationContext(), "Current Position Saved!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        Button showButton=(Button)findViewById(R.id.show);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PositionsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addLocationListener(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,this);
            }else{
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},666);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 666 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            addLocationListener();
        }
    }
    //Array hinzugefügt. arrayplätze werden bei aufruf der methode versetzt, pos0 ist der aktuelle standort
    //ausgabe der aktuellen pos mit rotem marker, die alten pos mit gelbem banner in schleife
    @Override
    public void onLocationChanged(Location location) {
        Log.i("LOCATION",location.getLatitude() + ":" + location.getLongitude());
        currentLat = location.getLatitude();
        currentLng = location.getLongitude();

        if(map != null){
            for(int i = 4; i>0;i--){
                posQ[i] = posQ[i-1];
            }
            posQ[0] = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions mo = new MarkerOptions();
            mo.title("Current Position");
            mo.position(posQ[0]);

            map.clear();
            map.moveCamera(CameraUpdateFactory.newLatLng(posQ[0]));
            map.addMarker(mo);

            for(int i = 1;i<5;i++){
                if(posQ[i] != null) {
                    MarkerOptions m2 = new MarkerOptions();
                    m2.title("Previous Position No " + i);
                    m2.position(posQ[i]);
                    m2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                    map.addMarker(m2);
                }
            }


        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }
}
