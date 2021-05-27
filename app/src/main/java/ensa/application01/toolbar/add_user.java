package ensa.application01.toolbar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class add_user extends AppCompatActivity {
    EditText FirstName;
    EditText LastName;
    EditText PhoneNumber;
    EditText Email;
    EditText StartDate;
    EditText FinishDate;
    EditText StartPlace;
    EditText FinishPlace;
    Button btn_add_user;
    ImageView back_Home;

    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Intent intent=getIntent();
        int size=intent.getIntExtra("size",5);
        back_Home=(ImageView)findViewById(R.id.lefticon);
        FirstName=(EditText)findViewById(R.id.first_name);
        LastName=(EditText)findViewById(R.id.last_name);
        PhoneNumber=(EditText)findViewById(R.id.phone_number);
        Email=(EditText)findViewById(R.id.email);
        StartPlace=(EditText)findViewById(R.id.start_place);
        FinishPlace=(EditText)findViewById(R.id.finish_place);
        btn_add_user=(Button)findViewById(R.id.add_user);
                                                        // DATE SETTINGS :
        StartDate=(EditText)findViewById(R.id.start_date);
        FinishDate=(EditText)findViewById(R.id.finish_date);

        back_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               profile();
            }
        });
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);//instancier un variable de type year calendier
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redefinir l 'instancier datePickerDialog
                DatePickerDialog datePickerDialog=new DatePickerDialog(add_user.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                        month1=month1+1;
                        String date=day1+"/"+month1+"/"+year1;
                        StartDate.setText(date);
                    }
                },year,month,day);//afecter year1,month1,day1 a year,month,day
                datePickerDialog.show();
            }
        });

        FinishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redefinir l 'instancier datePickerDialog
                DatePickerDialog datePickerDialog=new DatePickerDialog(add_user.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                        month1=month1+1;
                        String date=day1+"/"+month1+"/"+year1;
                        FinishDate.setText(date);
                    }
                },year,month,day);//affecter year1,month1,day1 a year,month,day
                datePickerDialog.show();
            }
        });

                                                       // ERROR SETTINGS :

        btn_add_user.setOnClickListener(new View.OnClickListener() {
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

                                         //**ADD DATA TO DATABASE

                HashMap<String,String> mdata=new HashMap<String,String>();
                mdata.put("arrive_position"," ");
                mdata.put("current_position"," ");
                mdata.put("email",Email.getText().toString());
                mdata.put("finish_date",FinishDate.getText().toString());
                mdata.put("finish_place",FinishPlace.getText().toString());
                mdata.put("first_name",FirstName.getText().toString());
                mdata.put("id","1");
                mdata.put("last_name",LastName.getText().toString());
                mdata.put("phone",PhoneNumber.getText().toString());
                mdata.put("start_date",StartDate.getText().toString());
                mdata.put("start_place",StartPlace.getText().toString());
                mdata.put("start_position"," ");
                mdata.put("tache"," ");








                chek_data(ADD_DATA_SUCSESFULY,mdata,size);
            }
        });

    }
    void chek_data(Boolean x,HashMap<String,String> mdata,int size1){
        if(x==true){

            add_data(mdata,size1+1);
            Toast.makeText(this,"ADD SUCCESFULY",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"THERE IS AN ERROR",Toast.LENGTH_SHORT).show();
        }
    }

    public void add_data(HashMap<String,String> mdata,int i){
        DatabaseReference mDataBase;
        mDataBase= FirebaseDatabase.getInstance().getReference();
        mDataBase.getDatabase().getReference().child("User_"+i);
        mDataBase.child("User_"+i).setValue(mdata);
    }

    public void profile(){
        Intent back=new Intent(this, MainActivity.class);
        startActivity(back);
    }

}