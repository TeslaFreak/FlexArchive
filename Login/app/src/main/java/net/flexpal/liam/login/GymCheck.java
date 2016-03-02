package net.flexpal.liam.login;

/**
 * Created by Liam on 12/18/2015.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class GymCheck  extends AsyncTask<String,Void,String> {
    Context context;


    GymCheck(Context cont){
        this.context = cont;
    }

        protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String uUsername = (String)arg0[0];


            String link="http://www.flexpal.net/webservice/currentinfo.php";
            String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(uUsername, "UTF-8");

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