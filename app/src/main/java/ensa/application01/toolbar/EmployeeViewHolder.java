package ensa.application01.toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class EmployeeViewHolder extends RecyclerView.Adapter<EmployeeViewHolder.ViewHolder>
{
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Employee> employeesFull = new ArrayList<>();

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

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
        holder.Name.setText(employees.get(position).getFirst_name());
        holder.prenom.setText(employees.get(position).getLast_name());
        holder.phone.setText(employees.get(position).getPhone());
        holder.email.setText(employees.get(position).getEmail());

        int[] images = {R.drawable.user1,R.drawable.user2,R.drawable.user4,R.drawable.user5,R.drawable.user6};
        Random rand = new Random();
        holder.img.setImageResource(images[rand.nextInt(images.length)]);
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context, employees.get(position).getFirst_name(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit_user=new Intent(context,EditUser.class);

                Intent intent = new Intent(context,MainActivity1.class);
                Bundle b=new Bundle();
                b.putString("email",employees.get(position).getEmail());
                int x1=employees.size();
                b.putString("size",""+x1);
                int x=position+1;
                b.putString("User_id", ""+x);
                String current_position=employees.get(position).getCurrent_position();
                String arrive_position=employees.get(position).getArrive_position();
                String start_position=employees.get(position).getStart_position();
                String id=employees.get(position).getId();
                String tache=employees.get(position).getTache();
                b.putString("current_position",""+current_position);
                b.putString("arrive_position",""+arrive_position);
                b.putString("start_position",""+start_position);
                b.putString("id",""+id);
                b.putString("tache",""+tache);
                b.putString("first_name",employees.get(position).getFirst_name());
                b.putString("last_name",employees.get(position).getLast_name());


                intent.putExtras(b);
                edit_user.putExtras(b);
                context.startActivity(edit_user);
                //Toast.makeText(context, employees.get(position).getEmail().trim(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
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
