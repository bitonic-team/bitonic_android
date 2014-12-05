package fr.intechinfo.bitonic.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Thunderya on 04/12/2014.
 */
public class Place implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;
    public double lat;
    public double lng;
    public String name;
    public String description;
    public List<String> organizations;

    public Place()
    {

    }
}
