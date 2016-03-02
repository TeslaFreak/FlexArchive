package net.flexpal.liam.login;

/**
 * Created by Liam on 11/29/2015.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class SignInActivity  extends AsyncTask<String,Void,String> {
    Context context;

    SignInActivity(Context cont){
        this.context = cont;
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String uEmail = (String)arg0[0];
            String uPassword = (String)arg0[1];
            String uUsername = (String)arg0[2];
            int uExpertise = Integer.parseInt((String) arg0[3]);
            int uAge =  Integer.parseInt((String)arg0[4]);
            String uBirthdate = (String)arg0[5];

            String link="http://www.flexpal.net/webservice/userinfo.php";
            String data  = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(uEmail, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(uPassword, "UTF-8");
            data += "&" + "expertise" + "=" + uExpertise;
            data += "&" + URLEncoder.encode("birthdate", "UTF-8") + "=" + URLEncoder.encode(uBirthdate, "UTF-8");
            data += "&" + "age" + "=" + uAge;
            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(uUsername, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
                break;
            }
            return sb.toString();
        }
        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }


    @Override
    protected void onPostExecute(String result){

    }
}
