package com.bhatt.milan.kisanhubdemo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhatt.milan.kisanhubdemo.model.Farms;
import com.bhatt.milan.kisanhubdemo.model.Field;
import com.bhatt.milan.kisanhubdemo.model.Field_Geometry;
import com.bhatt.milan.kisanhubdemo.model.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class GeoLocationFragment extends Fragment {

    public static final int GEOLOCATION_REQUEST = 2;
    private List<GeoLocation> geoLocationList;
    private GoogleMap googleMap;
    private MapView mapView;
    private boolean isPermissionGranted;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadJSONFromAsset();
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    displayAlert(getString(R.string.permission_alert_title), getString(R.string.permission_alert_geolocation_description));
                    return false;
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GEOLOCATION_REQUEST);
                    return false;
                }
            }
        }

        return true;
    }

    private void displayAlert(String title, String description) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GEOLOCATION_REQUEST);
                    }
                })
                .setNegativeButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == GEOLOCATION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true;
            displayMarker();
            displayPolyLine();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.geolocation_layout, container, false);

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        MapsInitializer.initialize(getActivity().getApplicationContext());

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                googleMap = map;
            }
        });
        return view;
    }

    private void displayPolyLine() {
        if (isPermissionGranted) {
            if (geoLocationList != null && !geoLocationList.isEmpty() && googleMap != null) {
                GeoLocation geoLocation = geoLocationList.get(0);
                List<Field> fields = geoLocation.getFields();
                for (Field field : fields) {
                    Field_Geometry field_geometry = field.getGeometry();
                    List<List<Float>> coordinates = field_geometry.getCoordinates().get(0);
                    LatLng[] latLngs = new LatLng[coordinates.size()];
                    for (int i = 0; i < coordinates.size(); i++) {
                        latLngs[i] = new LatLng(coordinates.get(i).get(1), coordinates.get(i).get(0));
                    }
                    Polyline polyline = googleMap.addPolyline(new PolylineOptions()
                            .clickable(false)
                            .add(latLngs));
                }
            }
        } else {
            Toast.makeText(getActivity(), getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
        }
    }

    private void displayMarker() {
        if (isPermissionGranted) {
            googleMap.setMyLocationEnabled(true);

            if (geoLocationList != null && !geoLocationList.isEmpty() && googleMap != null) {
                GeoLocation geoLocation = geoLocationList.get(0);
                List<Farms> farms = geoLocation.getFarms();
                for (Farms farm : farms) {
                    LatLng latLng = new LatLng(farm.getGeometry().getCoordinates().get(1), farm.getGeometry().getCoordinates().get(0));
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(farm.getProperties().getFarmName()).snippet(farm.getProperties().getFarmLocation()));

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        } else {
            Toast.makeText(getActivity(), getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


    }

    private void loadJSONFromAsset() {
        String json;

        try {
            InputStream inputStream = getActivity().getAssets().open("geojson.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            Gson gson = new GsonBuilder().create();
            geoLocationList = Arrays.asList(gson.fromJson(jsonArray.toString(), GeoLocation[].class));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getActivity() != null) {
            isPermissionGranted = checkPermission();
            displayMarker();
            displayPolyLine();
        }
    }
}
