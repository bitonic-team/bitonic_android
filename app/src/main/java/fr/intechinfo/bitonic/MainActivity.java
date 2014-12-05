package fr.intechinfo.bitonic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private GoogleMap mMap;
    List<Marker> markers = new ArrayList<Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Google Maps
        setContentView(R.layout.googlemaps);

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        //geolocalisation
        /*
        mMap.setMyLocationEnabled(true);

        CurrentLocationProvider currentLP = new CurrentLocationProvider(this);
        mMap.setLocationSource(currentLP);*/

        //it works
        Thread thread = new Thread() {
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();

                HttpGet request = new HttpGet("http://178.62.27.132/api/places/");
                String response = "";
                ResponseHandler<String> handler = new BasicResponseHandler();
                try {
                    response = client.execute(request, handler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("pas d'erreur",response);
            }
        };
        thread.start();

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
