package com.example.crud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.crud.InicioActivity.EXTRA_CORREO;
import static com.example.crud.InicioActivity.EXTRA_NOMBRE;

public class PersonDetailsActivity extends AppCompatActivity {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.email)
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra(EXTRA_NOMBRE);
        String correo = intent.getStringExtra(EXTRA_CORREO);

        name.setText(nombre);
        email.setText(correo);


    }
}
