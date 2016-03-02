package net.flexpal.liam.login;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class UserProfileActivity extends AppCompatActivity implements OnClickListener {

    Button back;
    Button edit;
    EditText txtExp;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtAge;
    EditText txtBirth;
    String expertise;
    String email;
    String password;
    String age;
    String birth;
    TextView name;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        back = (Button) findViewById(R.id.backButton);
        edit = (Button) findViewById(R.id.editButton);
        txtExp = (EditText) findViewById(R.id.expertiseText);
        txtAge = (EditText) findViewById(R.id.ageText);
        txtBirth = (EditText) findViewById(R.id.birthText);
        txtEmail = (EditText) findViewById((R.id.emailText));
        txtPassword = (EditText) findViewById(R.id.passwordText);
        name = (TextView) findViewById(R.id.userNameLabel);

        name.setText("Welcome: " + (LoginActivity.getWhoUser()));
        try {
            String jsonString = new InfoCheck(this).execute(LoginActivity.getWhoUser()).get();
            JSONObject jsonObject = new JSONObject(jsonString);
            email = jsonObject.getString("email");
            password = jsonObject.getString("password");
            expertise = jsonObject.getString("expertise");
            age = jsonObject.getString("age");
            birth = jsonObject.getString("birthdate");

            txtExp.setHint(expertise);
            txtEmail.setHint(email);
            txtPassword.setHint(password);
            txtAge.setHint(age);
            txtBirth.setHint(birth);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        back.setOnClickListener((OnClickListener) this);
        edit.setOnClickListener((OnClickListener) this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editButton:
                try {
                    newInfo();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.backButton:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UserProfile Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://net.flexpal.liam.login/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UserProfile Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://net.flexpal.liam.login/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void newInfo() throws ExecutionException, InterruptedException {

        String strEmail=txtEmail.getText().toString().toLowerCase();
        String strPassword=txtPassword.getText().toString();
        String strExpertise=txtExp.getText().toString();
        String strAge=txtAge.getText().toString();
        String strBirth=txtBirth.getText().toString();
        String strUsername = LoginActivity.getWhoUser();
        boolean change = false;

        if(!TextUtils.isEmpty(strEmail)){
            email = strEmail;
            if(!isEmail(email)){
                txtEmail.setError("Must be a valid email");
                return;
            }
            change = true;
        }
        if(!TextUtils.isEmpty(strPassword)){
            password = strPassword;
            change = true;
        }
        if(!TextUtils.isEmpty(strExpertise)){
            expertise = strExpertise;
            if(!isExpertise(expertise)){
                txtExp.setError("Must enter an expertise level between 1 and 10");
                return;
            }
            change = true;
        }
        if(!TextUtils.isEmpty(strAge)){
            age = strAge;
            if(!isAge(age)){
                txtAge.setError("Must enter a numerical age greater than 0");
                return;
            }
            change = true;
        }
        if(!TextUtils.isEmpty(strBirth)){
            birth = strBirth;
            if(!isDate(birth)){
                txtBirth.setError("Enter in format MM/DD");
                return;
            }
            change = true;
        }
        if(change) {

            if (updateInfo(email, password, strUsername, expertise, age, birth)) {
                Intent i = new Intent(this, UserProfileActivity.class);
                startActivity(i);
            } else {
                txtEmail.setError("This email " + strEmail + " is already in use.Try to login with that account or enter a new email");
                email = txtEmail.getHint().toString();
            }
        }
    }

    public Boolean isEmail(String email){
        if(email.contains("@")){
            return(true);
        }else{
            return(false);
        }
    }


    public Boolean isExpertise(String exp){
        int level;
        try{
            level=Integer.parseInt(exp);
        }catch(Exception e){
            return(false);
        }
        if(level >= 1 && level <= 10){
            return(true);
        }else{
            return(false);
        }
    }

    public Boolean isAge(String years){
        int age;
        try{
            age=Integer.parseInt(years);
        }catch(Exception e){
            return(false);
        }
        if(age >= 0){
            return(true);
        }else{
            return(false);
        }
    }

    public Boolean isDate(String date){
        int month;
        int day;
        try {
            month=Integer.parseInt(date.substring(0,2));
            day=Integer.parseInt(date.substring(3));
        }catch(Exception e){
            return(false);
        }

        if(date.length() == 5 && date.charAt(2) == '/' && month <= 12 && month >= 1 && day >= 1 && day <= 31){
            return(true);
        }else{
            return(false);
        }
    }


    public boolean updateInfo(String email, String password, String username, String expertise, String age, String birth) throws ExecutionException, InterruptedException {
        String check = new UpdateUserInfo(this).execute(email, password, username, expertise, age, birth).get();
        if(check.contains("success")){
            return true;
        }else {
            return false;
        }

    }
}
