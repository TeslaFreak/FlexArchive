package net.flexpal.liam.login;

/**
 * Created by Liam on 12/20/2015.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import android.content.Context;
import android.os.AsyncTask;


public class GymName  extends AsyncTask<String,Void,String> {
    Context context;

    GymName(Context cont) {
        this.context = cont;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        try {


            String link = "http://www.flexpal.net/webservice/gym_name.php";

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }


    @Override
    protected void onPostExecute(String result) {

    }
}