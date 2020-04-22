package com.example.crud;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.crud.model.Persona;
import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<Persona> persons;
    private OnItemClickListener listener;

    public PersonAdapter(ArrayList<Persona> persons) {

        this.persons = persons;
    }

    @Override
    public void onClick(View view) {

    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
        void OnDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row, viewGroup, false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.ViewHolder viewHolder, int i) {
        Persona person = persons.get(i);
        viewHolder.tvName.setText("Nombre: " + person.getNombre());
        viewHolder.tvEmail.setText("Correo: " + person.getCorreo());
    }
    @Override
    public int getItemCount() {
        return persons.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvName, tvEmail;
        ImageView imageDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            imageDel= itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }
                }
            });

            imageDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnDeleteClick(position);
                        }
                    }

                }
            });

        }
    }
}
