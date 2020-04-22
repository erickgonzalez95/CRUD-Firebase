package com.example.crud;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import com.example.crud.model.Persona;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.crud.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class InicioActivity extends AppCompatActivity implements PersonAdapter.OnItemClickListener {

    ArrayList<Persona> persons;
    RecyclerView rv;
    RecyclerView rvPersons;
    FirebaseDatabase fdb;
    Intent intentEdit;
    DatabaseReference reference;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    PersonAdapter pa;
    Dialog myDialog;
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_NOMBRE = "nombre";
    public static final String EXTRA_CORREO = "correo";
    public static final String EXTRA_APP = "app";
    public static final String EXTRA_PASS = "pass";
    String pnombre,pcorreo,papp,ppass,puid;

    @Override
    public void OnDeleteClick(int position) {

        Persona p = persons.get(position);

        puid = p.getUid();
        pnombre = p.getNombre();

       reference.child("persona").child(puid).removeValue();


        Toast.makeText(this,"Borrado correctamente", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        ButterKnife.bind(this);

        fdb = FirebaseDatabase.getInstance();
        // fdb.setPersistenceEnabled(true);
        reference = fdb.getReference();
        myDialog = new Dialog(this);
        rvPersons = findViewById(R.id.recycler);
        rvPersons.setLayoutManager(new LinearLayoutManager(this));
        persons = new ArrayList<>();
        listarDatos();
        pa = new PersonAdapter(persons);
        pa.setOnItemClickListener(InicioActivity.this);



    }
    public void ShowPopup() {

        TextView txtName,txtEmail,txtclose;
        Button editar;


        myDialog.setContentView(R.layout.custom_popup);
        txtName = myDialog.findViewById(R.id.name);
        txtName.setText(pnombre);
        txtEmail = myDialog.findViewById(R.id.email);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtEmail.setText(pcorreo);
        editar = myDialog.findViewById(R.id.btnfollow);


        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentEdit = new Intent(getApplicationContext(), EditPersonActivity.class);
                intentEdit.putExtra(EXTRA_ID,puid);
                intentEdit.putExtra(EXTRA_NOMBRE,pnombre);
                intentEdit.putExtra(EXTRA_CORREO,pcorreo);
                intentEdit.putExtra(EXTRA_APP,papp);
                intentEdit.putExtra(EXTRA_PASS,ppass);
                startActivity(intentEdit);
            }
        });
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
    private void listarDatos() {

        reference.child("persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                persons.clear();
                for (DataSnapshot objDs : dataSnapshot.getChildren()) {
                    Persona p = objDs.getValue(Persona.class);
                    persons.add(p);
                    // arrayAdapterPersona = new ArrayAdapter<Persona>
                    //      (MainActivity.this, R.layout.row, listPerson);
                    //  lvDatosPersonas.setAdapter(arrayAdapterPersona);
                }
                rvPersons.setAdapter(pa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.floatingActionButton)
    public void onViewClicked() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void OnItemClick(int position) {
        Persona p = persons.get(position);

        puid = p.getUid();
        pnombre = p.getNombre();
        pcorreo = p.getCorreo();
        papp = p.getApellidos();
        ppass = p.getPassword();


        ShowPopup();
    }
}
