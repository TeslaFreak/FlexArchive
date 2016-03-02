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
import android.os.AsyncTask;
import android.widget.TextView;

public class GymMaker  extends AsyncTask<String,Void,String> {
    Context context;

    GymMaker(Context cont){
        this.context = cont;
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String uName = (String)arg0[0];
            String uAddress = (String)arg0[1];
            String uPhone = (String)arg0[2];
            int uSquat = Integer.parseInt((String) arg0[3]);
            int uBench =  Integer.parseInt((String)arg0[4]);
            int uLeg = Integer.parseInt((String) arg0[5]);
            int uSmith =  Integer.parseInt((String)arg0[6]);
            int uDumbbell = Integer.parseInt((String) arg0[7]);
            int uTreadmill =  Integer.parseInt((String)arg0[8]);


            String link="http://www.flexpal.net/webservice/gymregister.php";
            String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(uName, "UTF-8");
            data += "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(uAddress, "UTF-8");
            data += "&" + URLEncoder.encode("phonenumber", "UTF-8") + "=" + URLEncoder.encode(uPhone, "UTF-8");
            data += "&" + "squat" + "=" + uSquat;
            data += "&" + "bench" + "=" + uBench;
            data += "&" + "legs" + "=" + uLeg;
            data += "&" + "smith" + "=" + uSmith;
            data += "&" + "dumbbell" + "=" + uDumbbell;
            data += "&" + "treadmill" + "=" + uTreadmill;


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