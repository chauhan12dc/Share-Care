package com.example.portfolio.ui.share;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.portfolio.DatabaseHelper;
import com.example.portfolio.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.LOCATION_SERVICE;

public class ShareFragment extends Fragment implements OnMapReadyCallback {

    MapView mapView;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Location currentLocation;
    protected static GoogleMap googleMapRef;
    static int count = 0;
    TextView destinationAddress;
    LatLng latLng;
    static String update_string = "no update";

    DatabaseHelper databaseHelper;
    
    Button add;


    private ShareViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share_ride, container, false);

        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        databaseHelper = new DatabaseHelper(this.getContext());
        
        add = root.findViewById(R.id.button6);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText sAdd = root.findViewById(R.id.sAdd);
                EditText dAdd = root.findViewById(R.id.dAdd);
                EditText date = root.findViewById(R.id.date);
                EditText amount = root.findViewById(R.id.amount);
                Spinner type = root.findViewById(R.id.type);
                EditText seat = root.findViewById(R.id.seat);
                EditText carno = root.findViewById(R.id.carno);

                if (databaseHelper.AddShare(sAdd.getText().toString(),dAdd.getText().toString(),date.getText().toString(),amount.getText().toString(),type.getSelectedItem().toString(),seat.getText().toString(),carno.getText().toString())){
                    Toast.makeText(root.getContext(), "Share Added Successfully!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(root.getContext(), "Error! Please Try Again Later!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION",}, 1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                currentLocation = location;
                if (count == 0 ) {
                    googleMapRef.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("My Location"));
                    googleMapRef.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13.0f));
                    count++;
                }
            }
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


            }
        });

        return root;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapRef = googleMap;

        if (update_string.equals("update"))
        {
            googleMap.addMarker(new MarkerOptions().title("Searched location").position(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13.0f));
            update_string = "no update";
            return;
        }
    }

    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


}