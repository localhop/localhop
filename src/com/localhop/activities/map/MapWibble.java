package com.localhop.activities.map;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.localhop.R;

public class MapWibble extends Activity {
    private static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    private static final LatLng KIEL = new LatLng(53.551, 9.993);

    private GoogleMap mMap;

    @Override protected void onCreate(@Nullable final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.map_wibble);

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        final Marker hamburg = mMap.addMarker(new MarkerOptions().position(HAMBURG).title("Hamburg"));
        final Marker kiel = mMap.addMarker(new MarkerOptions()
            .position(KIEL)
            .title("Kiel")
            .snippet("Kiel is cool")
            .icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_launcher)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

//    @Override public boolean onCreateOptionsMenu(@NonNull final Menu menu) {
//        getMenuInflater().inflate(R.menu.map_w);
//    }
}
