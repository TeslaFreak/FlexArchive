package net.flexpal.liam.login;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class FindFriends  extends AppCompatActivity {

    ListView findFriendsList;
    List<String> usernames;
    List<String> expertise;
    List<String> emails;
    String userExpertise;
    String curUsername = LoginActivity.getWhoUser();
    int[] images = {R.drawable.flex};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        try {
            String jsonString = new InfoCheck(this).execute(LoginActivity.getWhoUser()).get();
            JSONObject jsonObject = new JSONObject(jsonString);
            userExpertise = jsonObject.getString("expertise");

            String jsonString2 = new getFriendsData(this).execute(userExpertise).get();
            JSONArray array = new JSONArray(jsonString2);

            usernames = new ArrayList<String>();
            expertise = new ArrayList<String>();
            emails = new ArrayList<String>();

            for(int i = 0; i < array.length(); i++){
                String name = array.getJSONObject(i).get("username").toString();

                if(!curUsername.equalsIgnoreCase(name)) {
                    usernames.add(array.getJSONObject(i).get("username").toString());
                    expertise.add(array.getJSONObject(i).get("expertise").toString());
                    emails.add(array.getJSONObject(i).get("email").toString());
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Resources res = getResources();

        findFriendsList = (ListView) findViewById(R.id.findFriends);
        myAdapter adapter = new myAdapter(this, usernames, expertise, emails, images);
        findFriendsList.setAdapter(adapter);
    }
}

class myAdapter extends ArrayAdapter<String>
{
    Context context;
    int[] images;
    List<String> usernames;
    List<String> expertise;
    List<String> emails;

    myAdapter(Context c, List<String> usernames, List<String> expertise, List<String> emails, int images[])
    {
        super(c,R.layout.single_row,R.id.textView4, usernames);
        this.context = c;
        this.images = images;
        this.usernames = usernames;
        this.expertise = expertise;
        this.emails = emails;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row =inflator.inflate(R.layout.single_row, parent, false);

        ImageView myImage = (ImageView) row.findViewById(R.id.imageView);
        TextView myUsername = (TextView) row.findViewById(R.id.textView4);
        TextView myExpertise = (TextView) row.findViewById(R.id.expertise);
        TextView myEmail = (TextView) row.findViewById(R.id.emails);

        // set image to flex image
        myImage.setImageResource(images[0]);
        myUsername.setText("Username: " + usernames.get(position));
        myExpertise.setText("Expertise: " + expertise.get(position));
        myEmail.setText("Email: " + emails.get(position));


        return row;
    }
}