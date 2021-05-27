
package ensa.application01.toolbar;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.view.GravityCompat;
        import androidx.drawerlayout.widget.DrawerLayout;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.Toolbar;

        import com.google.android.material.navigation.NavigationView;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;

        import java.util.HashMap;

public class employee_profile extends AppCompatActivity {
    private TextView CompleteName;
    private TextView PhoneNumber;
    private TextView Email;
    private TextView StartDate;
    private TextView FinishDate;
    private TextView StartPlace;
    private TextView FinishPlace;
    private String id;
    private String nom;
    private String prenom;
    private String tache;
    public FirebaseAuth.AuthStateListener mauth;
    public ImageView notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        Intent i=getIntent();
        notification=(ImageView)findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(employee_profile.this,map.class);
                Bundle b=new Bundle();
                b.putString("fist_name",nom);
                b.putString("last_name",prenom);
                b.putString("user_id1",id);
                b.putString("tache",tache);

                /**********************/
                i.putExtras(b);
                startActivity(i);
            }
        });
        setupfirebaselistner();
        prenom = i.getStringExtra("Last_Name");
        nom = i.getStringExtra("First_Name");
        id = i.getStringExtra("user_id");
        tache = i.getStringExtra("tache");


        CompleteName=(TextView)findViewById(R.id.completename);
        PhoneNumber=(TextView)findViewById(R.id.phonenumber_profile);
        Email=(TextView)findViewById(R.id.email_profile);
        StartDate=(TextView)findViewById(R.id.startdate_profile);
        FinishDate=(TextView)findViewById(R.id.finishdate_profile);
        StartPlace=(TextView)findViewById(R.id.startplace_profile);
        FinishPlace=(TextView)findViewById(R.id.finishplace_profile);
        HashMap<String,String> profile=new HashMap<String,String>();
        HashMap<String,String> mission=new HashMap<String,String>();
        CompleteName.setText(i.getStringExtra("First_Name")+" "+i.getStringExtra("Last_Name"));
        PhoneNumber.setText(i.getStringExtra("Phone"));
        Email.setText(i.getStringExtra("Email"));
        StartDate.setText(i.getStringExtra("Start_Date"));
        FinishDate.setText(i.getStringExtra("Finish_Date"));
        StartPlace.setText(i.getStringExtra("Start_Place"));
        FinishPlace.setText(i.getStringExtra("Finish_Place"));


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
                if(user!=null){
                    Toast.makeText(employee_profile.this, "Sign In", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(employee_profile.this, "no one is Sign In", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

}