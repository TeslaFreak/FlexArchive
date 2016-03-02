package net.flexpal.liam.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity implements OnClickListener{

    Button back;
    Button register;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtUsername;
    EditText txtExpertise;
    EditText txtAge;
    EditText txtBirth;
    TextView usernameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        back=(Button)findViewById(R.id.backButton);
        register=(Button)findViewById(R.id.registerButton);
        txtEmail=(EditText)findViewById(R.id.emailText);
        txtPassword=(EditText)findViewById(R.id.passwordText);
        txtUsername=(EditText)findViewById(R.id.nameText);
        txtExpertise=(EditText)findViewById(R.id.expertiseText);
        txtAge=(EditText)findViewById(R.id.ageText);
        txtBirth=(EditText)findViewById(R.id.birthText);
        usernameView=(TextView)findViewById(R.id.userNameTextView);

        back.setOnClickListener((OnClickListener) this);
        register.setOnClickListener((OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerButton:
                try {
                    registerInfo();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.backButton:
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
        }
    }

    public void registerInfo() throws ExecutionException, InterruptedException {
        String strEmail=txtEmail.getText().toString().toLowerCase();
        String strPassword=txtPassword.getText().toString();
        String strUsername=txtUsername.getText().toString();
        String strExpertise=txtExpertise.getText().toString();
        String strAge=txtAge.getText().toString();
        String strBirth=txtBirth.getText().toString();

        if(TextUtils.isEmpty(strEmail)){
            txtEmail.setError("Must enter an email");
            return;
        }else if(TextUtils.isEmpty(strPassword)){
            txtPassword.setError("Must enter a password");
            return;
        }else if(TextUtils.isEmpty(strUsername)){
            txtPassword.setError("Must enter a username");
            return;
        }else if(TextUtils.isEmpty(strExpertise)){
            txtPassword.setError("Must enter an expertise level");
            return;
        }else if(TextUtils.isEmpty(strAge)){
            txtPassword.setError("Must enter an age");
            return;
        }else if(TextUtils.isEmpty(strBirth)){
            txtPassword.setError("Must enter a birthdate");
            return;
        }else if(!isEmail(strEmail)){
            txtEmail.setError("Must be a valid email");
            return;
        }else if(!isDate(strBirth)){
            txtBirth.setError("Enter in format MM/DD");
            return;
        }else if(!isAge(strAge)){
            txtAge.setError("Must enter a numerical age greater than 0");
            return;
        }else if(!isExpertise(strExpertise)){
            txtExpertise.setError("Must enter an expertise level between 1 and 10");
            return;
        }else {

            if(storeInfo(strEmail, strPassword,strUsername, strExpertise, strAge, strBirth)) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }else {
                txtEmail.setError("This email "+strEmail+" may already be in use. Go back to login below or enter a new email");
                txtUsername.setError("The username "+strUsername+" is already in use. Please enter another.");
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


    public boolean storeInfo(String email, String password, String username, String expertise, String age, String birth) throws ExecutionException, InterruptedException {
        String check = new SignInActivity(this).execute(email, password, username, expertise, age, birth).get();
        if(check.contains("success")){
            LoginActivity.setWhoUser(username);
            return true;
        }else {
            return false;
        }

    }
}

