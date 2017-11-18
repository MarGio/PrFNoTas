package com.example.gio.notas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class activity_notas extends AppCompatActivity {

    ListView lista;
    TextView txtbuscar;
    Button BotonCambioATareas;

    String operaciones[] =
            new String[]
                    {"Actualizar", "Eliminar"};
    private Notas note;
    public void  btnList_click(){
        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle("Operacion a Realizar")
                        .setIcon(R.mipmap.ic_launcher)
                        .setItems(operaciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(operaciones[which].equalsIgnoreCase(operaciones[0])){
                                    Intent siguiente = new Intent(getApplication(),activity_notas.class);


                                    siguiente.putExtra("operacion", "1");
                                    siguiente.putExtra("id", note.getId()+"");
                                    siguiente.putExtra("titulo", note.getTitulo());
                                    siguiente.putExtra("descripcion", note.getDescripcion());
                                    startActivityForResult(siguiente,1001);

                                }

                                if(operaciones[which].equalsIgnoreCase(operaciones[1])){
                                    confirmacion();
                                }


                                dialog.dismiss();
                            }
                        })
                        .create();

        dialog.show();
    }

    public void confirmacion(){

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle("Esta Seguro de Eliminar")
                        .setIcon(android.R.drawable.ic_delete)
                        .setMessage("Si elimina este registro no se podra recuperar")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DaoNotas dao = new DaoNotas(getBaseContext());
                                if(dao.delete(note.getId()+"")>0){
                                    Toast.makeText(getBaseContext(),"Nota Eliminada",Toast.LENGTH_SHORT).show();
                                    cargardatos();
                                }else{
                                    Toast.makeText(getBaseContext(),"Nota no eliminada",Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();

        dialog.show();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        BotonCambioATareas = findViewById(R.id.btntareas);
        BotonCambioATareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_notas.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        lista =(ListView)findViewById(R.id.listaNotas);
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Notas note= new Notas();
                note.setId(adp.getItem(i).getId());
                note.setTitulo(adp.getItem(i).getTitulo());
                note.setDescripcion(adp.getItem(i).getDescripcion());

                btnList_click();
                return false;
            }
        });


        cargardatos();

        startService(new Intent(getBaseContext(),Servicio.class));
    }
    ArrayAdapter<Notas> adp;
    public void cargardatos(){
        DaoNotas dao = new DaoNotas(activity_notas.this);
        adp = new ArrayAdapter<Notas>(activity_notas.this,
                android.R.layout.simple_list_item_1,dao.getAllNotificacines());
        lista.setAdapter(adp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode == 1000)
        {

            try {
//*/*/*/*/*/*/*/*/*/
                //obtener el objeto contacto
                Notas objcontacto = (Notas) data.getSerializableExtra("minota");

                DaoNotas dao = new DaoNotas(activity_notas.this);
                if(dao.insert(new Notas(objcontacto.getTitulo(),objcontacto.getDescripcion()))>0) {
                    Toast.makeText(getBaseContext(), "Nota Insertado", Toast.LENGTH_SHORT).show();
                    cargardatos();
                }else{
                    Toast.makeText(getBaseContext(), "No se pudo Insertar el Nota", Toast.LENGTH_SHORT).show();
                }



            }catch (Exception err){
                Toast.makeText(getBaseContext(),err.getMessage(),Toast.LENGTH_LONG).show();
            }

        }else if((resultCode==RESULT_OK && requestCode == 1001) || requestCode == 1002){
            try {

                //obtener el objeto contacto
                Notas objcontacto = (Notas) data.getSerializableExtra("minota");

                DaoNotas dao = new DaoNotas(activity_notas.this);
                Notas note = new Notas();
                note.setId(objcontacto.getId());
                note.setTitulo(objcontacto.getTitulo());
                note.setDescripcion(objcontacto.getDescripcion());
                cargardatos();

                if(dao.update(note)>0) {
                    Toast.makeText(getBaseContext(), "Nota Actualizada", Toast.LENGTH_SHORT).show();
                    cargardatos();
                }else{
                    Toast.makeText(getBaseContext(), "No se pudo Actualizar la Nota", Toast.LENGTH_SHORT).show();
                }



            }catch (Exception err){
                Toast.makeText(getBaseContext(),err.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.insertar) {
            Intent siguiente = new Intent(getApplication(),Datos.class);
            siguiente.putExtra("operacion", "0");
            startActivityForResult(siguiente,1000);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
