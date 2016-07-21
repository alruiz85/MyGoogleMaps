package es.alruiz.mymaps.main;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by AlfonsoRuiz on 22/06/2016.
 */
public class MainPresenter implements MainContract.UserActionsListener {

    private final MainContract.View view;
    private GoogleMap gMap;

    public MainPresenter(MainContract.View mapView) {
        this.view = mapView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.gMap = googleMap;
    }

}
