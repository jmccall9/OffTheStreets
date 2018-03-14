package com.example.tessamber.offthestreets.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tessamber.offthestreets.R;
import com.example.tessamber.offthestreets.model.HomelessShelter;
import com.example.tessamber.offthestreets.model.ShelterCollection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HomeScreen extends AppCompatActivity {
    public static String TAG = "MY_APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Button bLogout = findViewById(R.id.bLogout);
        bLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), WelcomeScreen.class);
                startActivityForResult(myIntent, 0);
            }
        });

        Button bLoadShelters = findViewById(R.id.bLoadFile);
        bLoadShelters.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });
    }

    /**
     * Button handler for the load button
     *
     * @param view  the actual button object that was pressed
     */
    public void onLoadButtonPressed(View view) {
        Log.v(HomeScreen.TAG, "Pressed the load button");
        readSDFile();
        Intent intent = new Intent(this, ShelterList.class);
        startActivity(intent);
    }

    //public static final int NAME_POSITION = 0;
    private static final String COMMA_DELIMITER = ",";
    /**
     * Open the HomelessShelterDatabase.csv file in the /res/raw directory
     *
     */
    private void readSDFile() {
        ShelterCollection model = ShelterCollection.INSTANCE;
        BufferedReader br = null;
        try {
            //Open a stream on the raw file
            InputStream is = getResources().openRawResource(R.raw.shelterdatabase);
            //From here we probably should call a model method and pass the InputStream
            //Wrap it in a BufferedReader so that we get the readLine() method
            //BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            br = new BufferedReader(new FileReader("shelterdatabase.csv"));

            String line;
            br.readLine(); //get rid of header line
            while ((line = br.readLine()) != null) {
                Log.d(HomeScreen.TAG, line);
                String[] shelterDetails = line.split(COMMA_DELIMITER);
                //String[] tokens = line.split(",");

                //HomelessShelter(int uniqueKey,String shelterName, int capacity,
                // String restrictions, double longitude, double latitude, String address,
                // String specialNotes, String phoneNumber)

                HomelessShelter shelter = new HomelessShelter(Integer.parseInt(shelterDetails[0]), shelterDetails[1],
                        Integer.parseInt(shelterDetails[2]), shelterDetails[3],
                        Double.parseDouble(shelterDetails[4]), Double.parseDouble(shelterDetails[5]),
                        shelterDetails[6], shelterDetails[7], shelterDetails[8]);

            }
            br.close();
        } catch (IOException e) {
            Log.e(HomeScreen.TAG, "error reading assets", e);
        }

    }


}