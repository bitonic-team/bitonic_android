package fr.intechinfo.bitonic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.intechinfo.bitonic.model.Place;


public class MainActivity extends Activity {

    private GoogleMap mMap;
    List<Marker> markers = new ArrayList<Marker>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Google Maps
        setContentView(R.layout.googlemaps);


        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        /*
        mMap.setMyLocationEnabled(true);

        CurrentLocationProvider currentLP = new CurrentLocationProvider(this);
        mMap.setLocationSource(currentLP);*/

        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(17.644022, -0.791016))
                .title("NOEL'S ASS IS HUGE")
                .snippet("NOEL IS A DISEASE"));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MainActivity.this, countryDetails.class);
                startActivity(intent);
            }
        });

        markers.add(marker);
        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
    }

    private void InitializePlaces(String service) throws IOException {
        HttpGet httpget = new HttpGet(service);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        String s = new DefaultHttpClient().execute(httpget, responseHandler);

        try {
            JSONObject jObj = new JSONObject(s);

            Place p = new Place();
            p.Id = jObj.getInt("_id");
            p.Title = jObj.getString("name");
          

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
}
