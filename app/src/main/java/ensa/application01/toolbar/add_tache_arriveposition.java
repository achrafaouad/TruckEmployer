package ensa.application01.toolbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class add_tache_arriveposition extends AppCompatActivity {

    EditText tache;
    EditText arrive_position;
    ImageView left_icon;
    Button add_mission;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tache_arriveposition);
        tache=(EditText)findViewById(R.id.tache);
        arrive_position=(EditText)findViewById(R.id.arrive_position);
        left_icon=(ImageView)findViewById(R.id.lefticon);
        add_mission=(Button)findViewById(R.id.add_mission);
        Intent home=getIntent();
        left_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h=new Intent(add_tache_arriveposition.this,MainActivity.class);
                startActivity(h);
            }
        });
        add_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean ADD_DATA_SUCSESFULY=true;
                if(TextUtils.isEmpty(tache.getText().toString())){
                    tache.setError("The item MISSION cannot be empty");
                    ADD_DATA_SUCSESFULY=false;
                }
                if(TextUtils.isEmpty(arrive_position.getText().toString())){
                    arrive_position.setError("The item ARRIVE POSITION cannot be empty");
                    ADD_DATA_SUCSESFULY=false;
                }
                chek_add_data(ADD_DATA_SUCSESFULY,home.getStringExtra("User_id").trim(),tache.getText().toString(),arrive_position.getText().toString());
            }
        });



    }


    void chek_add_data(Boolean x,String k,String tache,String arrive_position){
        if(x==true){
            FirebaseDatabase.getInstance().getReference().child("User_"+k).child("tache").setValue(tache);
            FirebaseDatabase.getInstance().getReference().child("User_"+k).child("finish_place").setValue(arrive_position);
            Toast.makeText(this,"ADD DATA SUCCESFULY",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"THERE IS AN ERROR",Toast.LENGTH_SHORT).show();
        }
    }



}