package com.e.multiplelocationmarker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.e.multiplelocationmarker.BottomSheet.MapBottomSheet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapMarkersActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;

    private Marker mLeicesterSquareMarker;
    private Marker mCoventGardenMarker;
    private Marker mPiccadillyCircusMarker;
    private Marker mEmbankmentMarker;
    private Marker mCharingCrossMarker;

    private static final LatLng Leicester_Square = new LatLng(51.510278, -0.130278);
    private static final LatLng Covent_Garden = new LatLng(51.51197, -0.1228);
    private static final LatLng Piccadilly_Circus = new LatLng(51.51, -0.134444);
    private static final LatLng Embankment = new LatLng(51.507, -0.122);
    private static final LatLng Charing_Cross = new LatLng(51.5073, -0.12755);
    private SupportMapFragment mMapFragment;
    private static final String TAG = "myTag";

    private static final String mAddressKey = "ADDRESS_KEY";
    private static final String LeicesterSquareStr = "Leicester Square";
    private static final String CoventGardenStr = "Covent Garden";
    private static final String PiccadillyCircusStr = "Piccadilly Circus";
    private static final String EmbankmentStr = "Embankment";
    private static final String CharingCrossStr = "Charing Cross";
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_markers);
        mContext = MapMarkersActivity.this;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askLocationPermission(1);
        } else {
            mMapFragment.getMapAsync(this);
        }
    }

    //Ask permeation for accessing gps location
    private void askLocationPermission(int statusCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}
                , statusCode);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                mMapFragment.getMapAsync(this);
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        try {
            if (mMap != null) {
                mMap.clear();

                BitmapDescriptor bitmapDescriptor = bitmapDescriptorFromVector(mContext);

                mMap = googleMap;
                mMap.setIndoorEnabled(false);
                mMap.getUiSettings().setZoomControlsEnabled(true);

                mLeicesterSquareMarker = mMap.addMarker(new MarkerOptions()
                        .position(Leicester_Square)
                        .title(LeicesterSquareStr));
                drawCircle(Leicester_Square);

                mCoventGardenMarker = mMap.addMarker(new MarkerOptions()
                        .position(Covent_Garden)
                        .title(CoventGardenStr));
                drawCircle(Covent_Garden);

                mPiccadillyCircusMarker = mMap.addMarker(new MarkerOptions()
                        .position(Piccadilly_Circus)
                        .title(PiccadillyCircusStr));
                drawCircle(Piccadilly_Circus);

                mEmbankmentMarker = mMap.addMarker(new MarkerOptions()
                        .position(Embankment)
                        .title(EmbankmentStr));
                drawCircle(Embankment);

                mCharingCrossMarker = mMap.addMarker(new MarkerOptions()
                        .position(Charing_Cross)
                        .title(CharingCrossStr));
                mCharingCrossMarker.setTag(0);
                drawCircle(Charing_Cross);

                if (bitmapDescriptor != null) {
                    setPropertiesOfMarker(mLeicesterSquareMarker, bitmapDescriptor);
                    setPropertiesOfMarker(mCoventGardenMarker, bitmapDescriptor);
                    setPropertiesOfMarker(mPiccadillyCircusMarker, bitmapDescriptor);
                    setPropertiesOfMarker(mEmbankmentMarker, bitmapDescriptor);
                    setPropertiesOfMarker(mCharingCrossMarker, bitmapDescriptor);
                }

                final View mapView = mMapFragment.getView();

                if (mapView != null && mapView.getViewTreeObserver().isAlive()) {
                    mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @SuppressLint("NewApi") // We check which build version we are using.
                        @Override
                        public void onGlobalLayout() {
                            LatLngBounds bounds = new LatLngBounds.Builder()
                                    .include(Leicester_Square)
                                    .include(Covent_Garden)
                                    .include(Piccadilly_Circus)
                                    .include(Embankment)
                                    .include(Charing_Cross)
                                    .build();

                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            } else {
                                mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }

                            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                        }
                    });
                }
                mMap.setOnMarkerClickListener(this);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        //googleMap.setMaxZoomPreference(15.0f);
    }

    private void setPropertiesOfMarker(Marker marker, BitmapDescriptor bitmapDescriptor) {
        if (marker != null) {
            marker.setIcon(bitmapDescriptor);
            marker.setTag(0);
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_marker_user);
        if (background != null) {
            background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());

            Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            background.draw(canvas);

            return BitmapDescriptorFactory.fromBitmap(bitmap);
        } else
            return null;
    }

    private void drawCircle(LatLng point) {

        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(point);

        // Radius of the circle
        circleOptions.radius(50);

        // Border color of the circle
        circleOptions.strokeColor(getResources().getColor(R.color.locationCircleRadiusColor));

        // Fill color of the circle
        circleOptions.fillColor(getResources().getColor(R.color.locationCircleColor));

        // Border width of the circle
        circleOptions.strokeWidth(2);

        // Adding the circle to the GoogleMap
        mMap.addCircle(circleOptions);

    }

    /**
     * Get Users current locations accurate address
     *
     * @param latitude  -> input latitude of current location
     * @param longitude -> input longitude of current location
     * @return -> returns current location accurate address
     */
    private String hereIsLocation(double latitude, double longitude) throws IOException {

        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses;
        addresses = geocoder.getFromLocation(latitude, longitude, 1);
        if (addresses.size() > 0) {
            return addresses.get(0).getAddressLine(0);
        }
        return null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String title = marker.getTitle();
        /*if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }*/
        switch (title) {
            case LeicesterSquareStr:
                try {
                    String address = hereIsLocation(Leicester_Square.latitude, Leicester_Square.longitude);
                    startBottomSheet(address);
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                }
                break;
            case CoventGardenStr:
                try {
                    String address = hereIsLocation(Covent_Garden.latitude, Covent_Garden.longitude);
                    startBottomSheet(address);
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                }
                break;
            case PiccadillyCircusStr:
                try {
                    String address = hereIsLocation(Piccadilly_Circus.latitude, Piccadilly_Circus.longitude);
                    startBottomSheet(address);
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                }
                break;
            case EmbankmentStr:
                try {
                    String address = hereIsLocation(Embankment.latitude, Embankment.longitude);
                    startBottomSheet(address);
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                }
                break;
            case CharingCrossStr:
                try {
                    String address = hereIsLocation(Charing_Cross.latitude, Charing_Cross.longitude);
                    startBottomSheet(address);
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                }
                break;

        }

        return true;
    }

    private void startBottomSheet(String address) {
        MapBottomSheet bottomSheetFragment = new MapBottomSheet();
        Bundle bundle = new Bundle();
        bundle.putString(mAddressKey, address);
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
}
