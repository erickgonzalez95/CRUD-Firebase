package com.example.crud;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crud.model.Persona;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    private ArrayList<Persona> listPerson = new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapterPersona;
    Persona personaSelected;

    FirebaseDatabase fdb;
    DatabaseReference reference;
    @BindView(R.id.txt_nombrePersona)
    EditText txtNombrePersona;
    @BindView(R.id.txt_appPersona)
    EditText txtAppPersona;
    @BindView(R.id.txt_correoPersona)
    EditText txtCorreoPersona;
    @BindView(R.id.txt_passwordPersona)
    EditText txtPasswordPersona;
    @BindView(R.id.lv_datosPersonas)
    ListView lvDatosPersonas;
    ArrayList<Persona> persons;
    RecyclerView rv;
    RecyclerView rvPersons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fdb = FirebaseDatabase.getInstance();
        // fdb.setPersistenceEnabled(true);
        reference = fdb.getReference();

        rvPersons = findViewById(R.id.rv);
        rvPersons.setLayoutManager(new LinearLayoutManager(this));
        persons = new ArrayList<>();
       // listarDatos();


        lvDatosPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                personaSelected = (Persona) adapterView.getItemAtPosition(i);
                txtNombrePersona.setText(personaSelected.getNombre());
                txtAppPersona.setText(personaSelected.getApellidos());
                txtCorreoPersona.setText(personaSelected.getCorreo());
                txtPasswordPersona.setText(personaSelected.getPassword());
            }
        });
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
                PersonAdapter pa = new PersonAdapter(persons);
                rvPersons.setAdapter(pa);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getDetails(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nombre = txtNombrePersona.getText().toString();
        String app = txtAppPersona.getText().toString();
        String correo = txtCorreoPersona.getText().toString();
        String pass = txtAppPersona.getText().toString();
        switch (item.getItemId()) {
            case R.id.icon_add: {

                break;

            }
            case R.id.icon_save: {
                Persona p = new Persona();
                p.setUid(personaSelected.getUid());
                p.setNombre(nombre.trim());
                p.setApellidos(app.trim());
                p.setCorreo(correo.trim());
                p.setPassword(pass.trim());
                reference.child("persona").child(p.getUid()).setValue(p);
                limpiarCajas();
                Toast.makeText(this, "Registro actualizado", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.icon_delete: {
                Persona p = new Persona();
                p.setUid(personaSelected.getUid());
                reference.child("persona").child(p.getUid()).removeValue();
                limpiarCajas();
                Toast.makeText(this, "registro borrado", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void limpiarCajas() {

        txtNombrePersona.setText("");
        txtAppPersona.setText("");
        txtCorreoPersona.setText("");
        txtPasswordPersona.setText("");
    }

    private void validacion() {
        String nombre = txtNombrePersona.getText().toString();
        String app = txtAppPersona.getText().toString();
        String correo = txtCorreoPersona.getText().toString();
        String pass = txtAppPersona.getText().toString();

        if (nombre.equals("")) {

            txtNombrePersona.setError("Campo requerido");
        } else if (app.equals("")) {
            txtAppPersona.setError("Campo requerido");
        } else if (correo.equals("")) {

            txtCorreoPersona.setError("Campo requerido");
        } else if (pass.equals("")) {
            txtPasswordPersona.setError("RCampo requerido");
        }
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        String nombre = txtNombrePersona.getText().toString();
        String app = txtAppPersona.getText().toString();
        String correo = txtCorreoPersona.getText().toString();
        String pass = txtAppPersona.getText().toString();
        if (nombre.equals("") || app.equals("") || correo.equals("") || pass.equals("")) {

            validacion();

        } else {
            Persona p = new Persona();
            p.setUid(UUID.randomUUID().toString());
            p.setNombre(nombre);
            p.setApellidos(app);
            p.setCorreo(correo);
            p.setPassword(pass);
            reference.child("persona").child(p.getUid()).setValue(p);
            limpiarCajas();
            Toast.makeText(this, "Agregado correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, InicioActivity.class);
            startActivity(intent);

        }
    }
}
