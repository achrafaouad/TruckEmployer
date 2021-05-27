package ensa.application01.toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity1 extends AppCompatActivity {
    private DrawerLayout dl;
    private Toolbar toolbar;
    private ImageView righticon;
    private ImageView lefticon;
    private TextView CompleteName;
    private TextView PhoneNumber;
    private TextView Email;

    private TextView StartDate;
    private TextView FinishDate;
    private TextView StartPlace;
    private TextView FinishPlace;
    private DatabaseReference mDataBase;
    static  Boolean stop;
    public FirebaseAuth.AuthStateListener mauth;
    static int index;
    static int i;
    static int number_employee;
    static String email_recycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupfirebaselistner();
        Intent intent_add_user=getIntent();
        dl=(DrawerLayout)findViewById(R.id.dl);
        righticon=(ImageView)findViewById(R.id.righticon);
        lefticon=(ImageView)findViewById(R.id.lefticon);
        NavigationView nav_new=(NavigationView)findViewById(R.id.nav_view);
        CompleteName=(TextView)findViewById(R.id.completename);
        PhoneNumber=(TextView)findViewById(R.id.phonenumber_profile);
        Email=(TextView)findViewById(R.id.email_profile);
        StartDate=(TextView)findViewById(R.id.startdate_profile);
        FinishDate=(TextView)findViewById(R.id.finishdate_profile);
        StartPlace=(TextView)findViewById(R.id.startplace_profile);
        FinishPlace=(TextView)findViewById(R.id.finishplace_profile);
        HashMap<String,String> profile=new HashMap<String,String>();
        Intent intent=getIntent();
        number_employee=Integer.parseInt(intent.getStringExtra("size"));
        String user_id=intent.getStringExtra("User_id");
        FirebaseDatabase.getInstance().getReference().child("User_"+user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    profile.put(snapshot1.getKey().toString(),snapshot1.getValue().toString());
                }
                String First_Name=profile.get("first_name");
                String Last_Name=profile.get("last_name");
                String Phone=profile.get("phone");
                String email=profile.get("email");
                String Start_Date=profile.get("start_date");
                String Finish_Date=profile.get("finish_date");
                String Finish_Place=profile.get("finish_place");
                String Start_Place=profile.get("start_place");

                CompleteName.setText(First_Name+" "+Last_Name);
                PhoneNumber.setText(Phone);
                Email.setText(email);
                StartDate.setText(Start_Date);
                FinishDate.setText(Finish_Date);
                StartPlace.setText(Finish_Place);
                FinishPlace.setText(Start_Place);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        lefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity1.this,MainActivity.class);
                startActivity(intent);
            }
        });

        righticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl.openDrawer(GravityCompat.START);
            }
        });

        nav_new.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int indice=item.getItemId();
                if(indice==R.id.user_position1){
                    Intent i1=new Intent(MainActivity1.this,mapAdmin.class);
                    Bundle b=new Bundle();
                    b.putString("email",intent.getStringExtra("email"));
                    b.putString("fist_name",intent.getStringExtra("first_name"));
                    b.putString("last_name",intent.getStringExtra("last_name"));
                    b.putString("tache",intent.getStringExtra("tache"));
                    b.putString("size",intent.getStringExtra("size"));
                    b.putString("user_id",intent.getStringExtra("User_id"));
                    i1.putExtras(b);
                    startActivity(i1);
                }
                if(indice==R.id.edituser){
                    edit_user();
                }
                if(indice==R.id.deleteuser){
                    int user_id=Integer.parseInt(intent.getStringExtra("User_id"));
                    final int d = user_id;
                    for(i=(d+1);i<=number_employee;i++){
                        HashMap<String,String> profile1=new HashMap<>();
                        FirebaseDatabase.getInstance().getReference().child("User_"+(i)).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snapshot1:snapshot.getChildren()){
                                    profile1.put(snapshot1.getKey().toString(),snapshot1.getValue().toString());

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        FirebaseDatabase.getInstance().getReference().child("User_"+(i-1)).setValue(profile1);

                    }
                   // FirebaseDatabase.getInstance().getReference().child("User_"+number_employee).removeValue();;
                    //Intent recycle_view=new Intent(MainActivity1.this,MainActivity.class);
                    //startActivity(recycle_view);



                }
                if(indice==R.id.Logout){
                    Intent i=new Intent(MainActivity1.this,Login.class);
                    startActivity(i);
                    MainActivity1.this.finish();
                }
                if(indice==R.id.Mission){
                    Intent add_mission=new Intent(MainActivity1.this,add_tache_arriveposition.class);
                    add_mission.putExtra("User_id",intent.getStringExtra("User_id"));
                    startActivity(add_mission);
                }

                return true;
            }
        });



    }
    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mauth);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(mauth!=null){
            FirebaseAuth.getInstance().removeAuthStateListener(mauth);

        }
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        if(mauth!=null){
            FirebaseAuth.getInstance().removeAuthStateListener(mauth);

        }
        FirebaseAuth.getInstance().signOut();
    }




    private void setupfirebaselistner(){
        mauth= new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();

            }
        };
    }
    public void add_user(){
        Intent i=new Intent(this,add_user.class);
        startActivity(i);
    }

    public void edit_user(){
        Intent i=new Intent(this,EditUser.class);
        startActivity(i);
    }




}