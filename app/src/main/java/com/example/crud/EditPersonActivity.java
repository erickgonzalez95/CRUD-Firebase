package com.example.crud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crud.model.Persona;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.crud.InicioActivity.EXTRA_APP;
import static com.example.crud.InicioActivity.EXTRA_CORREO;
import static com.example.crud.InicioActivity.EXTRA_ID;
import static com.example.crud.InicioActivity.EXTRA_NOMBRE;
import static com.example.crud.InicioActivity.EXTRA_PASS;

public class EditPersonActivity extends AppCompatActivity {

    FirebaseDatabase fdb;
    Intent intentEdit;
    DatabaseReference reference;
    @BindView(R.id.txt_nombrePersona)
    EditText txtNombrePersona;
    @BindView(R.id.txt_appPersona)
    EditText txtAppPersona;
    @BindView(R.id.txt_correoPersona)
    EditText txtCorreoPersona;
    @BindView(R.id.txt_passwordPersona)
    EditText txtPasswordPersona;


    String nombre, correo, app, pass, puid;
    @BindView(R.id.buttonEdit)
    Button buttonEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);
        ButterKnife.bind(this);
        fdb = FirebaseDatabase.getInstance();
        // fdb.setPersistenceEnabled(true);
        reference = fdb.getReference();
        Intent intent = getIntent();
        nombre = intent.getStringExtra(EXTRA_NOMBRE);
        correo = intent.getStringExtra(EXTRA_CORREO);
        app = intent.getStringExtra(EXTRA_APP); //apellidos
        pass = intent.getStringExtra(EXTRA_PASS);
        puid = intent.getStringExtra(EXTRA_ID);

        txtNombrePersona.setText(nombre);
        txtCorreoPersona.setText(correo);
        txtAppPersona.setText(app);
        txtPasswordPersona.setText(pass);
    }



    @OnClick(R.id.buttonEdit)
    public void onViewClicked() {
        String nombre = txtNombrePersona.getText().toString();
        String app = txtAppPersona.getText().toString();
        String correo = txtCorreoPersona.getText().toString();
        String pass = txtPasswordPersona.getText().toString();

        Persona p = new Persona();
        p.setUid(puid);
        p.setNombre(nombre.trim());
        p.setApellidos(app.trim());
        p.setCorreo(correo.trim());
        p.setPassword(pass.trim());
        reference.child("persona").child(puid).setValue(p);
        Toast.makeText(this, "Registro actualizado correctamente", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, InicioActivity.class);
        startActivity(intent);
    }
}
