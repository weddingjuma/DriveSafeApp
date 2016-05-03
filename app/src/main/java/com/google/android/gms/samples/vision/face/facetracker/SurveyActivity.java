package com.google.android.gms.samples.vision.face.facetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SurveyActivity extends AppCompatActivity {
    private static final String TAG = SurveyActivity.class.getSimpleName();

    private Button submitBtn;
    private RadioGroup group1;
    private RadioGroup group2;
    private RadioButton exhausted, verytired, bittired, nottired, local, highway;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        submitBtn = (Button)findViewById(R.id.submit);
        group1 = (RadioGroup)findViewById(R.id.group1);
        group2 = (RadioGroup)findViewById(R.id.group2);
        exhausted = (RadioButton)findViewById(R.id.exhausted);
        verytired = (RadioButton)findViewById(R.id.verytired);
        bittired = (RadioButton)findViewById(R.id.bittired);
        nottired = (RadioButton)findViewById(R.id.nottired);
        local = (RadioButton)findViewById(R.id.local);
        highway = (RadioButton)findViewById(R.id.highway);

        if (submitBtn != null) {
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tiredButton = group1.getCheckedRadioButtonId();
                    int localButton = group2.getCheckedRadioButtonId();

                    int frequency = 5000;  // Default
                    if (tiredButton == exhausted.getId()) {
                        frequency = 2000;
                    } else if (tiredButton == verytired.getId()) {
                        frequency = 4000;
                    } else if (tiredButton == bittired.getId()) {
                        frequency = 6000;
                    } else if (tiredButton == nottired.getId()) {
                        frequency = 8000;
                    }

                    if (localButton == highway.getId()) {  // remain same if local
                        frequency *= 0.5;
                    }
                    editor.putInt(getString(R.string.frequency_pref_key), frequency);
                    editor.commit();

                    // Go back to MainActivity (Parent Activity)
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_survey, menu);
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
