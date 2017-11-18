package com.example.gio.notas;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    TextView txtbuscar;
    Button BotonCambioANotas;

    String operaciones[] =
            new String[]
                    {"Actualizar", "Eliminar"};

    private Recordatorio recordatorio;
    public void  btnList_click(){
        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle("Operacion a Realizar")
                        .setIcon(R.mipmap.ic_launcher)
                        .setItems(operaciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(operaciones[which].equalsIgnoreCase(operaciones[0])){
                                    Intent siguiente = new Intent(getApplication(),DatosTarea.class);


                                    siguiente.putExtra("operacion", "1");
                                    siguiente.putExtra("id", recordatorio.getId()+"");
                                    siguiente.putExtra("titulo", recordatorio.getTitulo());
                                    siguiente.putExtra("descripcion", recordatorio.getDescripcion());
                                    siguiente.putExtra("fecha", recordatorio.getFecha());
                                    siguiente.putExtra("hora", recordatorio.getHora());
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

                                DaoRecordatorio dao = new DaoRecordatorio(getBaseContext());
                                if(dao.delete(recordatorio.getId()+"")>0){
                                    Toast.makeText(getBaseContext(),"Recordatorio Eliminado",Toast.LENGTH_SHORT).show();
                                    cargardatos();
                                }else{
                                    Toast.makeText(getBaseContext(),"Recordatorio no Eliminado",Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_main);
        BotonCambioANotas = findViewById(R.id.btnNotas);
        lista =(ListView)findViewById(R.id.listaContactos);
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                recordatorio = new Recordatorio();
                recordatorio.setId(adp.getItem(i).getId());
                recordatorio.setTitulo(adp.getItem(i).getTitulo());
                recordatorio.setDescripcion(adp.getItem(i).getDescripcion());
                recordatorio.setFecha(adp.getItem(i).getFecha());
                recordatorio.setHora(adp.getItem(i).getHora());

                btnList_click();
                return false;
            }
        });


        cargardatos();

        BotonCambioANotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,activity_notas.class);
                startActivity(intent);
                finish();
            }
        });
        startService(new Intent(getBaseContext(),Servicio.class));

    }

    ArrayAdapter<Recordatorio> adp;
    public void cargardatos(){
        DaoRecordatorio dao = new DaoRecordatorio(MainActivity.this);
        adp = new ArrayAdapter<Recordatorio>(MainActivity.this,
                android.R.layout.simple_list_item_1,dao.getAllNotificacines());
        lista.setAdapter(adp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode == 1000)
        {

            try {

                //obtener el objeto contacto
                Recordatorio objcontacto = (Recordatorio) data.getSerializableExtra("mirecordatorio");

                DaoRecordatorio dao = new DaoRecordatorio(MainActivity.this);
                if(dao.insert(new Recordatorio(objcontacto.getTitulo(),objcontacto.getDescripcion(),objcontacto.getFecha(),objcontacto.getHora()))>0) {
                    Toast.makeText(getBaseContext(), "Recordatorio Insertado", Toast.LENGTH_SHORT).show();
                    cargardatos();
                }else{
                    Toast.makeText(getBaseContext(), "No se pudo Insertar el Recordatorio", Toast.LENGTH_SHORT).show();
                }



            }catch (Exception err){
                Toast.makeText(getBaseContext(),err.getMessage(),Toast.LENGTH_LONG).show();
            }

        }else if((resultCode==RESULT_OK && requestCode == 1001) || requestCode == 1002){
            try {

                //obtener el objeto contacto
                Recordatorio objcontacto = (Recordatorio) data.getSerializableExtra("mirecordatorio");

                DaoRecordatorio dao = new DaoRecordatorio(MainActivity.this);
                Recordatorio recordatorio = new Recordatorio();
                recordatorio.setId(objcontacto.getId());
                recordatorio.setTitulo(objcontacto.getTitulo());
                recordatorio.setDescripcion(objcontacto.getDescripcion());
                recordatorio.setFecha(objcontacto.getFecha());
                recordatorio.setHora(objcontacto.getHora());
                cargardatos();

                if(dao.update(recordatorio)>0) {
                    Toast.makeText(getBaseContext(), "Recordatorio Actualizado", Toast.LENGTH_SHORT).show();
                    cargardatos();
                }else{
                    Toast.makeText(getBaseContext(), "No se pudo Actualizar el Recordatorio", Toast.LENGTH_SHORT).show();
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
            Intent siguiente = new Intent(getApplication(),DatosTarea.class);
            startActivityForResult(siguiente,1000);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
