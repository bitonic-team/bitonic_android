package fr.intechinfo.bitonic;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.intechinfo.bitonic.model.Place;


public class MainActivity extends Activity {

    private GoogleMap mMap;
    List<Marker> markers = new ArrayList<Marker>();
    Map<String, Place> places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Google Maps
        setContentView(R.layout.googlemaps);

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        places = new HashMap<String, Place>();

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Intent intent = new Intent(MainActivity.this, countryDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("country",places.get(marker.getTitle()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // call AsynTask to perform network operation on separate thread
        new HttpAsyncTask().execute("http://178.62.27.132/api/places/");
        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
    }

    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {

                JSONArray jr = new JSONArray(result);

                for (int i = 0; i < jr.length(); i++) {
                    JSONObject json_data = jr.getJSONObject(i);
                    Place p = new Place();
                    p.id = json_data.getString("_id");
                    p.name = json_data.getString("name");
                    p.lng = json_data.getDouble("lng");
                    p.lat = json_data.getDouble("lat");
                    p.description = json_data.getString("details");

                    List<String> list = new ArrayList<String>();
                    for (int j=0; j<json_data.getJSONArray("organizations").length(); j++) {
                        list.add( json_data.getJSONArray("organizations").getString(j));
                    }
                    p.organizations = list;

                    places.put(p.name,p);

                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(p.lat, p.lng))
                            .title(p.name)
                            .snippet(p.description));

                    markers.add(marker);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
