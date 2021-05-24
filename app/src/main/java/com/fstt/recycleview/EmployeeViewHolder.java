package com.fstt.recycleview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class EmployeeViewHolder extends RecyclerView.Adapter<EmployeeViewHolder.ViewHolder>
{
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Employee> employeesFull = new ArrayList<>();

    private Context context;
    public EmployeeViewHolder(Context c,ArrayList<Employee> employees) {
        this.context = c;
        this.employees = employees ;
        this.employeesFull = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //in this class we will instensiate our view by calling our inner class View holder
        //first we will create a view object
        View view = LayoutInflater.from(context).inflate( R.layout.row,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Name.setText(employees.get(position).getNom());
        holder.prenom.setText(employees.get(position).getPrenom());
        holder.phone.setText(employees.get(position).getTelephone());
        holder.email.setText(employees.get(position).getEmail());
        int[] images = {R.drawable.user1,R.drawable.user2,R.drawable.user4,R.drawable.user5,R.drawable.user6};
        Random rand = new Random();
        holder.img.setImageResource(images[rand.nextInt(images.length)]);
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context, employees.get(position).getNom(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,profil.class);
                intent.putExtra("name",employees.get(position).getNom());
                context.startActivity(intent);
                Toast.makeText(context, employees.get(position).getNom(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return employees.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //generate View object
        private TextView Name,prenom,phone,email;
        private ImageView img;
        private CardView parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parentcard);
            phone = itemView.findViewById(R.id.phone);
            prenom = itemView.findViewById(R.id.prenom);
            email = itemView.findViewById(R.id.email);
            Name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.image1);
        }
    }
}
