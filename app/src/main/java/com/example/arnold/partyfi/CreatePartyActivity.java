package com.example.arnold.partyfi;


import android.app.Fragment;

import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.location.Geocoder;

import android.location.Address;
import android.widget.Toast;


import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class CreatePartyActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_party, menu);
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
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private Button button;
        private String result;
        public PlaceholderFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_create_party, container, false);
            button = (Button) rootView.findViewById(R.id.btn_find);

            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View view) {


                    result = ((EditText) getActivity().findViewById(R.id.et_location)).getText().toString();
                    Thread thr = new Thread()
                    {
                      public void run()
                      {
                          Geocoder geocoder = new Geocoder( view.getContext(), Locale.getDefault());
                          if(!Geocoder.isPresent())
                          {
                              Toast.makeText(view.getContext(), "Geocoder Not Present!", Toast.LENGTH_LONG).show();
                          }
                          try
                          {
                              List<Address> list = geocoder.getFromLocationName(result, 1);
                              Address address = list.get(0);

                              double lat = address.getLatitude();
                              double lng = address.getLongitude();

                              String geodata = String.valueOf(lat) + "," + String.valueOf(lng);
                              Looper.prepare();
                              Toast.makeText(getActivity(), geodata, Toast.LENGTH_LONG).show();
                              Looper.loop();
                          }catch (IOException e)
                          {
                              Log.e("IOException", e.getMessage());
                          }



                      }
                    };
                    thr.start();
                }
            }
            );

            return rootView;
        }

    }

}
