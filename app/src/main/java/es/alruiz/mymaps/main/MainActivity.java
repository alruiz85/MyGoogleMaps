package es.alruiz.mymaps.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import es.alruiz.mymaps.R;
import es.alruiz.mymaps.base.BaseActivity;
import es.alruiz.mymaps.utils.LocationUtils;

public class MainActivity extends BaseActivity implements MainContract.View, OnMapReadyCallback {
    private MainContract.UserActionsListener mActionsListener;


    private static final int ERROR_DIALOG_REQUEST = 9001;
    private final String TAG = MainActivity.this.getClass().getSimpleName();
    private final int MAP_ZOOM = 12;
    LocationUtils location;
    GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActionsListener = new MainPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        location = new LocationUtils(this);
        if (!location.canGetLocation()) {
            location.showSettingsAlert();
        } else {
            location.getLocation();
        }

        if (!checkPlayServices()) {
            showSnackBar("There is a problem with Google Services", getWindow().getDecorView().getRootView());
        }

        initmap();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackBar("Pending", getWindow().getDecorView().getRootView());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        ERROR_DIALOG_REQUEST).show();
            }
            return false;
        }
        return true;
    }

    private void initmap() {
        if (gMap == null) {
            SupportMapFragment map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
            map.getMapAsync(this);
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mActionsListener.onMapReady(googleMap);
        goToLocation(latLng);

        gMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker()));

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return true;
            }
        });
    }

    private void goToLocation(LatLng latLng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, MAP_ZOOM);
        gMap.animateCamera(cameraUpdate);
    }

    @Override
    protected void onPause() {
        super.onPause();
        location.stopLocationUpdates();
    }
}
