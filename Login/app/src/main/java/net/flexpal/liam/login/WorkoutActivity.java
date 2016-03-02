package net.flexpal.liam.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorkoutActivity extends AppCompatActivity{
    String biceps_val = "0", triceps_val = "0", chest_val = "0", back_val = "0", shoulder_val = "0", legs_val = "0", calves_val = "0", abs_val = "0", cardio_val = "0", inTime, outTime, username, location, msg;
    Spinner gym;
    ArrayAdapter<String[]> adapter;
    EditText in, out;
    List<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        in = (EditText) findViewById(R.id.inTime);
        out = (EditText) findViewById(R.id.outTime);
        gym = (Spinner) findViewById(R.id.gym);

        try {
            String jsonString = new GymName(this).execute().get();
            JSONArray array = new JSONArray(jsonString);
            names = new ArrayList<String>();

            for(int i = 0; i < array.length(); i++){
                names.add(array.getJSONObject(i).get("name").toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = (ArrayAdapter<String[]>) new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gym.setAdapter(adapter);
        username = LoginActivity.getWhoUser();
    }

    public void submit_form(View view){
        location = gym.getSelectedItem().toString();
        inTime = in.getText().toString();
        outTime = out.getText().toString();
        boolean check = false;
        try {
            check = workoutStore(biceps_val, triceps_val, chest_val, back_val, shoulder_val, legs_val, calves_val, abs_val, cardio_val, inTime, outTime, username, location );
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if(check) {
            Context context = getApplicationContext();
            CharSequence text = "Workout logged successful";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Workout was not logged";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, msg, duration);
            toast.show();
            return;
        }

    }

    public void selectItem(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId())
        {
            case R.id.biceps:
                if(checked){
                    biceps_val = "1";
                }
                else{
                    biceps_val = "0";
                }
                break;

            case R.id.triceps:
                if(checked){
                    triceps_val = "1";
                }
                else{
                    triceps_val = "0";
                }
                break;

            case R.id.chest:
                if(checked){
                    chest_val = "1";
                }
                else{
                    chest_val = "0";
                }
                break;

            case R.id.shoulders:
                if(checked){
                    shoulder_val = "1";
                }
                else{
                    shoulder_val = "0";
                }
                break;

            case R.id.legs:
                if(checked){
                    legs_val = "1";
                }
                else{
                    legs_val = "0";
                }
                break;

            case R.id.calves:
                if(checked){
                    calves_val = "1";
                }
                else{
                    calves_val = "0";
                }
                break;

            case R.id.back:
                if(checked){
                    back_val = "1";
                }
                else{
                    back_val = "0";
                }
                break;

            case R.id.abs:
                if(checked){
                    abs_val = "1";
                }
                else{
                    abs_val = "0";
                }
                break;

            case R.id.cardio:
                if(checked){
                    cardio_val = "1";
                }
                else{
                    cardio_val = "0";
                }
                break;



        }


    }
    public void back(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

    public boolean workoutStore(String  biceps, String triceps, String chest, String back, String shoulder, String legs, String calves, String abs, String cardio, String inTime, String outTime, String username, String location) throws ExecutionException, InterruptedException {
        if(TextUtils.isEmpty(location)){
            msg = "Please select a gym location.";
            return false;
        }else if( TextUtils.isEmpty(inTime)){
            msg = "Please enter a start time.";
            return false;
        }else  if(TextUtils.isEmpty(outTime)){
            msg = "Please enter a stop time.";
            return false;
        }

        String check = new WorkoutMaker(this).execute(biceps, triceps, chest, back, shoulder, legs, calves, abs, cardio, inTime, outTime, username, location).get();
//msg = "biceps:" + biceps + ", triceps:" + triceps + ", chest:" + chest + ", back:" + back + ", shoulder:" + shoulder + ", legs:" + legs + ", calves:" + calves + ", abs:" + abs + ", cardio:" + cardio + ", inTime:" + inTime + ", outTime:" + outTime + ", username:" + username + ", location:" + location;
        msg = check;
        if(check.contains("1")){
            return true;
        }else {
            return false;
        }

    }

}
