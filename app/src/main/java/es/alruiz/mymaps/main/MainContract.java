package es.alruiz.mymaps.main;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by AlfonsoRuiz on 22/06/2016.
 */
public interface MainContract {
    interface View {

    }

    interface UserActionsListener {
        void onMapReady(GoogleMap googleMap);
    }
}
