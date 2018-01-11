package daempflok.googlemap;



import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;
import java.util.Queue;

public class PositionsActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback{
    protected LocationManager lm;
    protected GoogleMap map;
    private LatLng[] posQ = new LatLng[100];
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positions);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        addLocationListener();

        MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mf.getMapAsync(this);
        //FunctionCall for loading pos from DB
        loadPositions();

        //BUTTONS
        Button saveButton=(Button)findViewById(R.id.delete);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePositions();
            }
        });

        Button showButton=(Button)findViewById(R.id.back);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PositionsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    //loads datqa from db and converts to double and later to latlng
    public void loadPositions(){
        SQLiteDatabase db = openOrCreateDatabase("mysavedlocations.db", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS gps(ID INTEGER PRIMARY KEY AUTOINCREMENT, Latitude REAL, Longitude REAL);");

        Cursor c = db.rawQuery("SELECT Latitude, Longitude FROM gps",null);
        if (c.moveToFirst()){
            do {
                String lat = c.getString(0);
                String lng = c.getString(1);
                double lat1 = Double.parseDouble(lat);
                double lng1 = Double.parseDouble(lng);
                posQ[index] = new LatLng(lat1,lng1);
                index++;
            } while(c.moveToNext());
        }
        c.close();
        db.close();

    }
    //opens db and drops table
    public void deletePositions(){
        SQLiteDatabase dbx = openOrCreateDatabase("mysavedlocations.db", Context.MODE_PRIVATE, null);
        dbx.execSQL("drop table gps;");
        dbx.close();
        map.clear();
        Toast toast = Toast.makeText(getApplicationContext(), "Positions Deleted!", Toast.LENGTH_SHORT);
        toast.show();
    }
    //set markers and polyline
    public void setMarkers(){
        if(map != null){
            if(posQ[0] != null) {
                map.clear();
                map.moveCamera(CameraUpdateFactory.newLatLng(posQ[0]));

                for (int i = 0; i < posQ.length; i++) {
                    if (posQ[i] != null) {
                        MarkerOptions m2 = new MarkerOptions();
                        int y = i+1;
                        m2.title("Saved Position No. " + y);
                        m2.position(posQ[i]);
                        m2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        map.addMarker(m2);

                        if(posQ[i+1] != null){
                            Polyline line = map.addPolyline(new PolylineOptions()
                                    .add(posQ[i], posQ[i+1])
                                    .width(5)
                                    .color(Color.RED));
                        }
                    }
                }
            }

        }
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
    @Override
    public void onLocationChanged(Location location) {

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
        setMarkers();
    }
}
