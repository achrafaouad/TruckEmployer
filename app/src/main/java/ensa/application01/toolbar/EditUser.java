package ensa.application01.toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EditUser extends AppCompatActivity {
    private EditText FirstName;
    private EditText LastName;
    private EditText PhoneNumber;
    private EditText Email;
    private EditText StartDate;
    private EditText FinishDate;
    private EditText StartPlace;
    private EditText FinishPlace;
    private ImageView lefticon;
    Button btn_edit_user;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        FirstName=(EditText)findViewById(R.id.first_name);
        LastName=(EditText)findViewById(R.id.last_name);
        PhoneNumber=(EditText)findViewById(R.id.phone_number);
        Email=(EditText)findViewById(R.id.email);
        StartDate=(EditText)findViewById(R.id.start_date);
        FinishDate=(EditText)findViewById(R.id.finish_date);
        StartPlace=(EditText)findViewById(R.id.start_place);
        FinishPlace=(EditText)findViewById(R.id.finish_place);
        btn_edit_user=(Button)findViewById(R.id.edit_user);
        lefticon=(ImageView)findViewById(R.id.lefticon);
        ArrayList<String> profile=new ArrayList();
        //ArrayList<String> mission=new ArrayList();
        Intent home=getIntent();
        lefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5=new Intent(EditUser.this,MainActivity.class);
                startActivity(i5);
            }
        });

        mDataBase=FirebaseDatabase.getInstance().getReference().child("User_"+home.getStringExtra("User_id"));
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profile.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    profile.add(snapshot1.getValue().toString());
                }
                FirstName.setText(profile.get(5));
                LastName.setText(profile.get(7));
                PhoneNumber.setText(profile.get(8));
                Email.setText(profile.get(2));
                StartDate.setText(profile.get(9));
                FinishDate.setText(profile.get(3));
                StartPlace.setText(profile.get(10));
                FinishPlace.setText(profile.get(4));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Intent intent=getIntent();


        btn_edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean ADD_DATA_SUCSESFULY=true;
                if(TextUtils.isEmpty(FirstName.getText().toString())){
                    FirstName.setError("The item First Name cannot be empty");
                    ADD_DATA_SUCSESFULY=false;
                }
                if(TextUtils.isEmpty(LastName.getText().toString())){
                    LastName.setError("The item Last Name cannot be empty");
                    ADD_DATA_SUCSESFULY=false;
                }
                if(TextUtils.isEmpty(PhoneNumber.getText().toString())){
                    PhoneNumber.setError("The item Phone Number cannot be empty");
                    ADD_DATA_SUCSESFULY=false;
                }
                if(TextUtils.isEmpty(Email.getText().toString())){
                    Email.setError("The item Email cannot be empty");
                    ADD_DATA_SUCSESFULY=false;
                }
                if(TextUtils.isEmpty(StartDate.getText().toString())){
                    StartDate.setError("The item Start Date cannot be empty");
                    ADD_DATA_SUCSESFULY=false;
                }
                if(TextUtils.isEmpty(FinishDate.getText().toString())){
                    FinishDate.setError("The item Finish Date cannot be empty");
                    ADD_DATA_SUCSESFULY=false;
                }
                if(TextUtils.isEmpty(StartPlace.getText().toString())){
                    StartPlace.setError("The item Start Place cannot be empty");
                    ADD_DATA_SUCSESFULY=false;
                }
                if(TextUtils.isEmpty(FinishPlace.getText().toString())){
                    FinishPlace.setError("The item Finish Place cannot be empty");
                    ADD_DATA_SUCSESFULY=false;
                }
                HashMap<String,String> mdata=new HashMap<String,String>();
                mdata.put("arrive_position",home.getStringExtra("arrive_position"));
                mdata.put("current_position",home.getStringExtra("current_position"));
                mdata.put("first_name",FirstName.getText().toString());
                mdata.put("id",home.getStringExtra("id"));
                mdata.put("last_name",LastName.getText().toString());
                mdata.put("phone",PhoneNumber.getText().toString());
                mdata.put("email",Email.getText().toString());
                mdata.put("start_date",StartDate.getText().toString());
                mdata.put("finish_date",FinishDate.getText().toString());
                mdata.put("start_place",StartPlace.getText().toString());
                mdata.put("finish_place",FinishPlace.getText().toString());
                mdata.put("start_position",home.getStringExtra("start_position"));
                mdata.put("tache",home.getStringExtra("tache"));
                chek_data(ADD_DATA_SUCSESFULY,mdata,home.getStringExtra("User_id"));
            }
        });



    }
    public void edit_data(HashMap<String,String> mdata,String k){
        DatabaseReference mDataBase;
        mDataBase= FirebaseDatabase.getInstance().getReference();
        mDataBase.child("User_"+k).setValue(mdata);
    }

    void chek_data(Boolean x,HashMap<String,String> mdata,String k){
        if(x==true){
            edit_data(mdata,k);
            Toast.makeText(this,"EDIT DATA SUCCESFULY",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"THERE IS AN ERROR",Toast.LENGTH_SHORT).show();
        }
    }
}