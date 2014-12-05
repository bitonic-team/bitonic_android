package fr.intechinfo.bitonic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fr.intechinfo.bitonic.model.Place;

/**
 * Created by AurelAbidos on 04/12/2014.
 */
public class CountryDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.country_details);
        Place place = (Place) getIntent().getExtras().getSerializable("country");

        TextView nameTv = (TextView) findViewById(R.id.name);
        nameTv.setText(place.name);

        TextView description = (TextView) findViewById(R.id.description);
        description.setText(place.description);

        TextView organizations = (TextView) findViewById(R.id.organizations);
        String p = "Organisations :"+System.getProperty("line.separator");
        for( String s:place.organizations){
            p += System.getProperty("line.separator")+s;
        }
        organizations.setText(p);

        Button donateBtn = (Button) findViewById(R.id.donateBtn);
        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CountryDetails.this, "Vous avez bien effectué un don de 1€", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CountryDetails.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
