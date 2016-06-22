package es.alruiz.mymaps.main;

/**
 * Created by AlfonsoRuiz on 22/06/2016.
 */
public class MainPresenter implements MainContract.UserActionsListener {

    private final MainContract.View view;

    public MainPresenter(MainContract.View mapView) {
        this.view = mapView;
    }

    @Override
    public void onMapReady() {

    }
}
