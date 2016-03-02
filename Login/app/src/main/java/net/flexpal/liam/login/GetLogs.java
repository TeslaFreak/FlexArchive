package net.flexpal.liam.login;


import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.regex.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Async task class to get json by making HTTP call
 * */
public class GetLogs extends AsyncTask<String, Void, String> {

    Context context;
    GraphView gym;

    GetLogs(Context cont, GraphView gym){
        this.context = cont;
        this.gym = gym;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        try{

            int uGymid = Integer.parseInt(arg0[0]);

            String data  = "gymid" + "=" + uGymid;

            String link="http://www.flexpal.net/webservice/usagehistory.php";
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

        try{

            JSONArray array = new JSONArray(result);
            Date[] logins = new Date[array.length()];
            Date[] logouts = new Date[array.length()];
            //Pattern p1 = Pattern.compile("^\\S*.");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");

            for(int i = 0; i < array.length(); i++) {
                Date datein  = formatter.parse(array.getJSONObject(i).get("login_at").toString());
                logins[i] = datein;
                Date dateout  = formatter.parse(array.getJSONObject(i).get("logout_at").toString());
                logouts[i] = dateout;
                //Matcher m = p1.matcher(array.getJSONObject(i).get("login_at").toString());
                //String time = m.replaceFirst("");
                //time = time.replaceFirst(":",".");

                //logins[i] = Double.parseDouble(time.split(":")[0]);

            }

            DataPoint[] values = new DataPoint[logins.length];

            Calendar end = Calendar.getInstance();
            Calendar start = Calendar.getInstance();
            Date now = end.getTime();
            Date startDate = formatter.parse("2015-12-01 01:00:00.00");
            end.setTime(now);
            start.setTime(startDate);
            int loggedUsers = 0;

            for (Date date = start.getTime(); start.before(end); start.add(Calendar.MINUTE, 10), date = start.getTime()) {
                for(int i = 0; i < logins.length; i++)
                {
                    if(logins[i].before(now) && (logouts[i].after(now) || logouts[i] == null))
                        loggedUsers +=1;
                    else if (logins[i].before(now) && logouts[i].before(now))
                        loggedUsers -=1;

                    if(i == logins.length - 1)
                        values[i] = new DataPoint(date, loggedUsers);
                }
            }


            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(values);
            this.gym.addSeries(series);

        } catch(Exception e){
            Log.d("JSONException: ", e.getMessage());
        }

    }

}