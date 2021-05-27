package ensa.application01.toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    EditText Email;
    EditText Password;
    Button LoginAsAdmin;
    Button LoginAsEmployee;
    FirebaseAuth mAuth;
    DatabaseReference mDataBase;
    static int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email=(EditText)findViewById(R.id.inputEmail);
        Password=(EditText)findViewById(R.id.inputPassword);
        LoginAsAdmin=(Button)findViewById(R.id.btn_Admin);
        LoginAsEmployee=(Button)findViewById(R.id.btn_Employee);

        mAuth=FirebaseAuth.getInstance();
        HashMap<String,String> profile=new HashMap<String,String>();
        HashMap<String,String> mission=new HashMap<String,String>();
        LoginAsEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataBase=FirebaseDatabase.getInstance().getReference();
                mAuth.signInWithEmailAndPassword(Email.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            for(int i=1;i<4;i++) {
                                    Query q1 = FirebaseDatabase.getInstance().getReference().child("User_" + i);
                                    int finalI = i;
                                    q1.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                                profile.put(snapshot1.getKey().toString(), snapshot1.getValue().toString());
                                            }
                                            if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(profile.get("email")) && Integer.parseInt(profile.get("id")) == 1) {
                                                Intent intent2 = new Intent(Login.this, employee_profile.class);
                                                String First_Name = profile.get("first_name");
                                                String Last_Name = profile.get("last_name");
                                                String Phone = profile.get("phone");
                                                String Email = profile.get("email");
                                                String Start_Date = profile.get("start_date");
                                                String Finish_Date = profile.get("finish_date");
                                                String Finish_Place = profile.get("finish_place");
                                                String Start_Place = profile.get("start_place");
                                                String tache = profile.get("tache");
                                                Bundle b = new Bundle();
                                                b.putString("First_Name", First_Name);
                                                b.putString("user_id", "" +(finalI-1) );
                                                b.putString("Phone", Phone);
                                                b.putString("Last_Name", Last_Name);
                                                b.putString("Email", Email);
                                                b.putString("Start_Date", Start_Date);
                                                b.putString("Finish_Date", Finish_Date);
                                                b.putString("Start_Place", Start_Place);
                                                b.putString("Finish_Place", Finish_Place);
                                                b.putString("tache", tache);
                                                intent2.putExtras(b);

                                                //i.putExtra("mission",mission);
                                                startActivity(intent2);
                                                finish();

                                            } else {
                                                Toast.makeText(Login.this, "You Are not an employee", Toast.LENGTH_SHORT).show();
                                            }


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                            }

                        }
                        else{
                            Toast.makeText(Login.this,"You are not an employee",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        LoginAsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataBase=FirebaseDatabase.getInstance().getReference();
                mAuth.signInWithEmailAndPassword(Email.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            for(int i=1;i<4;i++){

                                Query q1 = FirebaseDatabase.getInstance().getReference().child("User_"+i);
                                q1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                                            profile.put(snapshot1.getKey().toString(),snapshot1.getValue().toString());
                                        }
                                        if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(profile.get("email")) && Integer.parseInt(profile.get("id"))==0){
                                            Intent i=new Intent(Login.this, MainActivity.class);
                                            startActivity(i);
                                            Login.this.finish();



                                        }
                                        else {
                                            Toast.makeText(Login.this, "You Are not an admin", Toast.LENGTH_SHORT).show();
                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }


                        }
                        else{
                            Toast.makeText(Login.this,"You are not an employee",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });










    }
    public void recycleview(){
        Intent i=new Intent(this,EditUser.class);
        startActivity(i);
    }
    public void profile(HashMap<String,String> profile,HashMap<String,String> mission){

    }








}