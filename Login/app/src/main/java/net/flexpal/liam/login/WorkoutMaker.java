package net.flexpal.liam.login;

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

public class WorkoutMaker  extends AsyncTask<String,Void,String> {
    Context context;

    WorkoutMaker(Context cont){
        this.context = cont;
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String biceps = (String)arg0[0];
            String triceps = (String)arg0[1];
            String chest = (String)arg0[2];
            String back = (String) arg0[3];
            String shoulder =  (String)arg0[4];
            String legs = (String) arg0[5];
            String calves =  (String)arg0[6];
            String abs = (String) arg0[7];
            String cardio =  (String)arg0[8];
            String inTime =  (String)arg0[9];
            String outTime =  (String)arg0[10];
            String username =  (String)arg0[11];
            String location =  (String)arg0[12];

            String link="http://www.flexpal.net/webservice/workout_log.php";
            String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8");
            data += "&" + URLEncoder.encode("biceps", "UTF-8") + "=" + URLEncoder.encode(biceps, "UTF-8");
            data += "&" + URLEncoder.encode("triceps", "UTF-8") + "=" + URLEncoder.encode(triceps, "UTF-8");
            data += "&" + URLEncoder.encode("chest", "UTF-8") + "=" + URLEncoder.encode(chest, "UTF-8");
            data += "&" + URLEncoder.encode("back", "UTF-8") + "=" + URLEncoder.encode(back, "UTF-8");
            data += "&" + URLEncoder.encode("shoulder", "UTF-8") + "=" + URLEncoder.encode(shoulder, "UTF-8");
            data += "&" + URLEncoder.encode("legs", "UTF-8") + "=" + URLEncoder.encode(legs, "UTF-8");
            data += "&" + URLEncoder.encode("calves", "UTF-8") + "=" + URLEncoder.encode(calves, "UTF-8");
            data += "&" + URLEncoder.encode("abs", "UTF-8") + "=" + URLEncoder.encode(abs, "UTF-8");
            data += "&" + URLEncoder.encode("cardio", "UTF-8") + "=" + URLEncoder.encode(cardio, "UTF-8");
            data += "&" + URLEncoder.encode("inTime", "UTF-8") + "=" + URLEncoder.encode(inTime, "UTF-8");
            data += "&" + URLEncoder.encode("outTime", "UTF-8") + "=" + URLEncoder.encode(outTime, "UTF-8");




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
            return new String("Exception: Here " + e.getMessage());
        }
    }


    @Override
    protected void onPostExecute(String result){

    }
}