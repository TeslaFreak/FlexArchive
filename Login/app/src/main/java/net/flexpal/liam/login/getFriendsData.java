package net.flexpal.liam.login;


import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class getFriendsData extends AsyncTask<String, Void, String> {
    Context context;

    getFriendsData(Context cont) {
        this.context = cont;
    }


    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            int uExpertise = Integer.parseInt((String) arg0[0]);
            String data  = "expertise" + "=" + uExpertise;

            String link = "http://www.flexpal.net/webservice/find_friends_post.php";

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
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    protected void onPostExecute(String result) {

    }
}
