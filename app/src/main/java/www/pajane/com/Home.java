package www.pajane.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity implements RecyclerViewInterface {

    private static final int REQUEST_CODE = 44;
    private static final String TAG = "Home";
    private TextView seeallnextdeparture;
    private RecyclerView busRecycleView, departedRecyclerView;
    private DatabaseReference database;
    private BusAdapter busAdapter;
    private ArrayList<Bus> list;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView locality;
    private ArrayAdapter<String> adapterCities;
    private Spinner spinner;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private ImageView ticketBtn;

    private TextView today;
    private Calendar calender;
    private SimpleDateFormat dateFormat;
    private String Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        seeallnextdeparture = findViewById(R.id.Nseealltxt);
        busRecycleView = findViewById(R.id.busRecyclerView);
        departedRecyclerView = findViewById(R.id.departedRecyclerView);
        locality = (TextView)findViewById(R.id.localityid);
        spinner = (Spinner) findViewById(R.id.spinner_cities);
        ticketBtn = (ImageView) findViewById(R.id.ticketBtn);
        today = (TextView) findViewById(R.id.today);

        adapterCities = new ArrayAdapter<String>(this,R.layout.color_spinner_layout,getResources().getStringArray(R.array.cities));
        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapterCities);
        calender = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, d MMM");

        Date = dateFormat.format(calender.getTime());
        today.setText(Date);



        ticketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Home.this,Receipt.class);
                startActivity(intent);
            }
        });


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback(){

            @Override
            public void onLocationResult(LocationResult locationResult) {

                try {
                    Geocoder geocoder = new Geocoder(Home.this, Locale.getDefault());
                    List<Address>  address = null;
                    if(locationResult == null){
                        return;
                    }

                    Location location = locationResult.getLocations().get(0);


                    address = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                    locality.setText(address.get(0).getLocality());




                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        };



        database = FirebaseDatabase.getInstance().getReference("Buses");
        busRecycleView.setHasFixedSize(true);
        departedRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        busRecycleView.setLayoutManager(layoutManager);
        departedRecyclerView.setLayoutManager(layoutManager1);


        list = new ArrayList<>();
        busAdapter = new BusAdapter(this, list, this);
        busRecycleView.setAdapter(busAdapter);
        departedRecyclerView.setAdapter(busAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Bus bus = dataSnapshot.getValue(Bus.class);
                    list.add(bus);
                }
                busAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        seeallnextdeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, departed.class);
                startActivity(intent);
            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){

            //getLastLocation();
            checkSettingAndStartLocationUpdate();

        }else
        {
            askLocationPermission();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdate();
    }


    private void checkSettingAndStartLocationUpdate() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();

        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);

        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // settings of the device are satisfied we can start location updates

                startLocationUpdate();

            }
        });

        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ResolvableApiException apiException = (ResolvableApiException) e;

                try {
                    apiException.startResolutionForResult(Home.this, 1001);
                } catch (IntentSender.SendIntentException sendIntentException) {
                    sendIntentException.printStackTrace();
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdate() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }

    private void stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }


    private void getLastLocation() {
            @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

            locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                        if( location != null )
                        {
                            Toast.makeText(Home.this, "location: "+location.toString(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Home.this, "location not identified", Toast.LENGTH_SHORT).show();
                        }
                }
            });

            locationTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Home.this, "Failed to get your location", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void askLocationPermission() {
        if( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                // permission granted
                //getLastLocation();

                checkSettingAndStartLocationUpdate();
            }else{
                // permission not granted
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBusCLicked(int position) {
        Intent intent = new Intent(Home.this, Moredetails.class);
        float am = list.get(position).getPrice();

        intent.putExtra("NAME", list.get(position).getName());
        intent.putExtra("FROM", list.get(position).getFrom());
        intent.putExtra("TO", list.get(position).getDestination());
        intent.putExtra("AMOUNT", Float.toString(am));
        intent.putExtra("STATION", list.get(position).getStation());

        intent.putExtra("from_home", true);

        startActivity(intent);
    }
}