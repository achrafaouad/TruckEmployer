package ensa.application01.toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mEmplyee;
    private DatabaseReference mdatabase;
    private EmployeeViewHolder myAdapter;
    private ArrayList<Employee> list=new ArrayList<>();
    private ArrayList<Employee> list2;
    static int size_index;

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        Intent intent=getIntent();
        mEmplyee = findViewById(R.id.recycleV);
        mEmplyee.setHasFixedSize(true);
        mEmplyee.setLayoutManager(new LinearLayoutManager(this));

        mdatabase = FirebaseDatabase.getInstance().getReference();
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floating1);
        Toast.makeText(this, "hana f lowel", Toast.LENGTH_SHORT).show();
        request2();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,add_user.class);
                intent.putExtra("size",size_index);
                startActivity(intent);
            }
        });
        };

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.search1);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s)
            {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                request1(s);

                return false;
            }
        }));
        return super.onCreateOptionsMenu(menu);
    }


    private void request1(String s) {

        list = new ArrayList<>();
        list2 = new ArrayList<>();
        myAdapter = new EmployeeViewHolder(this,list);
        mEmplyee.setAdapter(myAdapter);
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    Employee E = data.getValue(Employee.class);
                    list2.add(E);
                }
                if(s.length() != 0 ) {
                    for(Employee E : list2){
                        if(E.getFirst_name().toLowerCase().contains(s)){
                            list.add(E);
                        }
                    }
                }else {
                    list.clear();
                    list.addAll(list2);

                }


                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void request2() {
        list.clear();
        myAdapter = new EmployeeViewHolder(this,list);

        mEmplyee.setAdapter(myAdapter);
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    Employee E = data.getValue(Employee.class);
                    list.add(E);


                }
                size_index=list.size();
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
