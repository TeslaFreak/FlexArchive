package net.flexpal.liam.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (TextView)findViewById(R.id.usernameText);
        Button gym=(Button)findViewById(R.id.toGym);
        Button graph=(Button)findViewById(R.id.graphButton);
        Button profile=(Button)findViewById(R.id.profileButton);
        Button findFriends=(Button)findViewById(R.id.findFriends);
        Button workout=(Button)findViewById(R.id.workoutButton);


        gym.setOnClickListener((OnClickListener) this);
        graph.setOnClickListener((OnClickListener) this);
        profile.setOnClickListener((OnClickListener) this);
        findFriends.setOnClickListener((OnClickListener) this);
        workout.setOnClickListener((OnClickListener) this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.toGym:
                Intent i = new Intent(this, GymCreationActivity.class);
                startActivity(i);

                break;

            case R.id.graphButton:
                Intent j = new Intent(this, GymUsageActivity.class);
                startActivity(j);
                break;

            case R.id.profileButton:
                Intent k = new Intent(this, UserProfileActivity.class);
                startActivity(k);
                break;

            case R.id.findFriends:
                Intent l = new Intent(this, FindFriends.class);
                startActivity(l);
                break;

            case R.id.workoutButton:
                Intent m = new Intent(this, WorkoutActivity.class);
                startActivity(m);
                break;

        }

    }
}
