package com.example.gio.notas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Multimedia extends AppCompatActivity {

    private ArrayList<String> archivos;
    private ArrayAdapter<String> adaptador1;
    Button btnVideoN;
    private final String CARPETA_RAIZ = "multimedia/";
    private final String RUTA_VIDEO = CARPETA_RAIZ + "videos";
    final int COD_VIDEO = 30;
    String path,path2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        archivos = new ArrayList<String>();
        setContentView(R.layout.activity_multimedia);
        adaptador1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, archivos);
        ListView alv = (ListView) findViewById(R.id.listaVideos);
        alv.setAdapter(adaptador1);
        btnVideoN = findViewById(R.id.btnVideoN);
        btnVideoN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarVideo();
            }
        });

        alv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    Intent intent = new Intent();
                    String ruta = adaptador1.getItem(position).toString();
                    String archivo = "";


//                    if (ruta.endsWith(".jpg")){
//                        archivo = "image/*";
//
//                    }else
                    if (ruta.endsWith(".mp4")) {
                        archivo = "video/*";

                    }


                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(ruta), archivo);
                    startActivity(intent);

                } catch (Exception err) {
                    Toast.makeText(getBaseContext(), err.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
//                case COD_FOTO:
//
//                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
//                            new MediaScannerConnection.OnScanCompletedListener() {
//                                @Override
//                                public void onScanCompleted(String path, Uri uri) {
//                                    Log.i("Ruta de almacenamiento", "Path: " + path);
//                                }
//                            });
//
//                    Bitmap bitmap = BitmapFactory.decodeFile(path);
//
//                    break;
                case COD_VIDEO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento", "Path: " + path);
                                }
                            });
                default:
                    break;
            }

        }
    }

    private void grabarVideo() {
        File fileVid = new File(Environment.getExternalStorageDirectory(), RUTA_VIDEO);
        boolean isCreada2 = fileVid.exists();
        String nombrevid = "";
        if (!isCreada2) {
            isCreada2 = fileVid.mkdirs();
        }

        if (isCreada2) {
            nombrevid = (System.currentTimeMillis() / 1000) + ".mp4";
        }

        path2 = Environment.getExternalStorageDirectory() +
                File.separator + RUTA_VIDEO + File.separator + nombrevid;


        File vid = new File(path2);

        Intent intent = null;
        intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(vid));

        startActivityForResult(intent, COD_VIDEO);

        archivos.add(path2);
        adaptador1.notifyDataSetChanged();

    }
}
