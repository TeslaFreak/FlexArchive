package net.flexpal.liam.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    Button login;
    Button register;
    EditText txtEmail;
    EditText txtPassword;

    public static String whoUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button)findViewById(R.id.email_sign_in_button);
        register=(Button)findViewById(R.id.registerButton);
        txtEmail=(EditText)findViewById(R.id.email);
        txtPassword=(EditText)findViewById(R.id.password);

        login.setOnClickListener((OnClickListener) this);
        register.setOnClickListener((OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:
                Intent i = new Intent(this, RegisterActivity.class);
                startActivity(i);

                break;

            case R.id.email_sign_in_button:
                try {
                    signInAttempt();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    public void signInAttempt() throws ExecutionException, InterruptedException, JSONException {

        String strEmail=txtEmail.getText().toString().toLowerCase();
        String strPassword=txtPassword.getText().toString();

        if(TextUtils.isEmpty(strEmail)) {
            txtEmail.setError("Must enter an email");
            return;
        }else if(TextUtils.isEmpty(strPassword)){
            txtPassword.setError("Must enter a password");
            return;
        }else if(!isEmail(strEmail)){
            txtEmail.setError("Must be a valid email");
            return;
        }else {
            if(checkLogInfo(strEmail, strPassword)) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }else {

                Context context = getApplicationContext();
                CharSequence text = "Email and Password combination was not found. Try Again!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }
        }
    }

    public Boolean isEmail(String email) {
        if(email.contains("@")){
            return(true);
        }else{
            return(false);
        }
    }

    public Boolean checkLogInfo(String email, String password) throws ExecutionException, InterruptedException, JSONException {

        LoginCheck checker = new LoginCheck(this);
        String check = checker.execute(email, password).get();
        if(check.contains("1")){
            JSONObject jsonObject = new JSONObject(check);
            String username = jsonObject.getString("username");
            setWhoUser(username);
            return(true);
        }else{

            return(false);
        }
    }

    public static void setWhoUser(String who){
        whoUser = who;
    }

    public static String getWhoUser() {
        return(whoUser);
    }
}
