package net.flexpal.liam.login;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

public class GymCreationActivity extends AppCompatActivity implements OnClickListener {

    Button createGym;
    Button back;
    EditText txtGymName;
    EditText txtSquat;
    EditText txtBench;
    EditText txtLeg;
    EditText txtSmith;
    EditText txtDumbbell;
    EditText txtTreadmill;
    EditText txtAddress;
    EditText txtPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_creation);

        back=(Button)findViewById(R.id.backButton);
        createGym=(Button)findViewById(R.id.createButton);
        txtGymName=(EditText)findViewById(R.id.gymNameText);
        txtSquat=(EditText)findViewById(R.id.squatText);
        txtBench=(EditText)findViewById(R.id.benchText);
        txtLeg=(EditText)findViewById(R.id.legText);
        txtSmith=(EditText)findViewById(R.id.smithText);
        txtDumbbell=(EditText)findViewById(R.id.freeText);
        txtTreadmill=(EditText)findViewById(R.id.treadmillText);
        txtAddress=(EditText)findViewById(R.id.addressText);
        txtPhone=(EditText)findViewById(R.id.phoneText);

        back.setOnClickListener((OnClickListener) this);
        createGym.setOnClickListener((OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;

            case R.id.createButton:
                try {
                    registerGym();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void registerGym() throws ExecutionException, InterruptedException {

        String strGymName = txtGymName.getText().toString();
        String strSquat = txtSquat.getText().toString();
        String strBench = txtBench.getText().toString();
        String strLeg = txtLeg.getText().toString();
        String strSmith = txtSmith.getText().toString();
        String strDumbbell = txtDumbbell.getText().toString();
        String strTreadmill = txtTreadmill.getText().toString();
        String strAddress = txtAddress.getText().toString();
        String strPhone = txtPhone.getText().toString();


        if (TextUtils.isEmpty(strGymName)) {
            txtGymName.setError("Please enter a gym name.");
            return;
        }else if (TextUtils.isEmpty(strSquat)){
            txtSquat.setError("Enter amount of Squat Racks");
            return;
        }else if (TextUtils.isEmpty(strBench)){
            txtBench.setError("Enter amount of Bench Presses");
            return;
        }else if (TextUtils.isEmpty(strLeg)){
            txtLeg.setError("Enter amount of Leg Presses");
            return;
        }else if (TextUtils.isEmpty(strSmith)){
            txtSmith.setError("Enter amount of Smith Machines");
            return;
        }else if (TextUtils.isEmpty(strDumbbell)){
            txtDumbbell.setError("Enter amount of Dumbbell Sets");
            return;
        }else if (TextUtils.isEmpty(strTreadmill)){
            txtTreadmill.setError("Enter amount of Treadmills");
            return;
        }else if (TextUtils.isEmpty(strPhone)){
            txtPhone.setError("Enter the Phone Number");
            return;
        }else if (TextUtils.isEmpty(strAddress)) {
            txtAddress.setError("Enter the address");
            return;
        }else{
            if(gymStore(strGymName, strAddress, strPhone, strSquat, strBench, strLeg, strSmith, strDumbbell, strTreadmill )){
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }else{
                txtGymName.setError("Gym with this name has already been made.");
            }

        }

    }

    public boolean gymStore(String name, String address, String phone, String squat, String bench, String legs, String smith, String dumbbell, String treadmill) throws ExecutionException, InterruptedException {
        String check = new GymMaker(this).execute(name, address, phone, squat, bench, legs, smith, dumbbell,treadmill).get();
        if(check.contains("1")){
            return true;
        }else {
            return false;
        }

    }

}
