package com.example.gio.notas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Datos extends AppCompatActivity implements Datos_Nota.OnFragmentInteractionListener, View.OnClickListener{

    Button guardar;
    Button actualizar;

    EditText titulo;
    EditText descripcion;

    private String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        mapear();
        insertar();
        actualizar();

        try {
            Bundle bundle = getIntent().getExtras();


            if (bundle.getString("operacion").equalsIgnoreCase("0")) {
                actualizar.setVisibility(View.INVISIBLE);

            } else if (bundle.getString("operacion").equalsIgnoreCase("1")) {
                id = bundle.getString("id");
                //Toast.makeText(getBaseContext(),id,Toast.LENGTH_SHORT).show();
                titulo.setText(bundle.getString("titulo"));
                descripcion.setText(bundle.getString("descripcion"));
                guardar.setVisibility(View.INVISIBLE);
            }
        }catch (Exception err){}
    }

    public void mapear(){
        actualizar = (Button)findViewById(R.id.btnactualizar);

        titulo = (EditText) findViewById(R.id.txttitulo);
        descripcion = (EditText) findViewById(R.id.txtdescripcion);
    }

    public void insertar(){
        guardar=(Button)findViewById(R.id.btnguardarNota);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent atras = new Intent();


                Notas alum = new Notas();
                alum.setTitulo(titulo.getText().toString());
                alum.setDescripcion(descripcion.getText().toString());

                atras.putExtra("minota", alum);

                setResult(RESULT_OK, atras);
                finish();


            }
        });
    }

    public void actualizar(){
        actualizar=(Button)findViewById(R.id.btnactualizar);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent atras = new Intent();

                Notas alum = new Notas();

                alum.setId(Integer.parseInt(id));
                alum.setTitulo(titulo.getText().toString());
                alum.setDescripcion(descripcion.getText().toString());

                atras.putExtra("minota", alum);

                setResult(RESULT_OK, atras);
                finish();


            }
        });
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {

    }
}
