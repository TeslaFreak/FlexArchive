package net.flexpal.liam.login;


import android.util.Log;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.widget.ArrayAdapter;

/**
 * Created by Chris on 12/13/2015.
 */
public class GetGymInfo extends AsyncTask<String, Void, String> {

    Context context;
    TextView gymName;
    TextView gymAddress;
    TextView gymPhone;
    GridView equipGrid;

    GetGymInfo(Context cont, TextView GymName, TextView GymAddress, TextView GymPhone, GridView EquipGrid){
        this.context = cont;
        this.gymName = GymName;
        this.gymAddress = GymAddress;
        this.gymPhone = GymPhone;
        this.equipGrid = EquipGrid;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        try{

            int uGymid = Integer.parseInt(arg0[0]);

            String data  = "gymid" + "=" + uGymid;

            String link="http://www.flexpal.net/webservice/GymInfo.php";
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
            JSONObject jobject = array.getJSONObject(0);

            gymName.setText(jobject.get("name").toString());
            gymAddress.setText(jobject.get("address").toString());
            gymPhone.setText(jobject.get("phonenumber").toString());
            List<String> equiplist;
            equiplist=new ArrayList<String>();
            equiplist.add("Squat Racks: " + jobject.get("squat").toString());
            equiplist.add("Benches: " + jobject.get("bench").toString());
            equiplist.add("Leg Machines: " + jobject.get("legs").toString());
            equiplist.add("Smith Machines: " + jobject.get("smith").toString());
            equiplist.add("Dumbbell Sets: " + jobject.get("dumbbell").toString());
            equiplist.add("Treadmills: " + jobject.get("treadmill").toString());

            // Create a new ArrayAdapter
            final ArrayAdapter<String> gridViewArrayAdapter = new ArrayAdapter<String>
                    (context,android.R.layout.simple_list_item_1, equiplist);

            // Data bind GridView with ArrayAdapter (String Array elements)
            equipGrid.setAdapter(gridViewArrayAdapter);

        } catch(Exception e){
            Log.d("JSONException: ", e.getMessage());
        }
    }
}
