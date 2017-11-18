package com.example.gio.notas;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Servicio extends Service {

    @Override
    public void onCreate(){

    }

    hilo h;
    @Override
    public int onStartCommand(Intent intent,int flag,int idProcess){

        if(h==null) {
            h = new hilo();
            h.start();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy(){

        if(h.isAlive()) {
            h.stop();
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private ArrayList<Recordatorio> listarecordatorios = new ArrayList<>() ;
    int x=0;
    public void btnNoti_click(String Titulo,String Descripcion,int indice) {

        x=x+1;
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        int icono = R.mipmap.calendar;

        long hora = System.currentTimeMillis();

        Intent i = new Intent(this, DatosTarea.class);


        i.putExtra("operacion", "1");
        i.putExtra("id",listarecordatorios.get(indice).getId()+"");
        i.putExtra("titulo",  listarecordatorios.get(indice).getTitulo());
        i.putExtra("descripcion",  listarecordatorios.get(indice).getDescripcion());
        i.putExtra("fecha",  listarecordatorios.get(indice).getFecha());
        i.putExtra("hora", listarecordatorios.get(indice).getHora());



        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1002, i, 0);

        mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle(Titulo)
                .setContentText(Descripcion)
                .setWhen(hora)
                .setVibrate(new long[]{100, 250, 100, 500})
                .setAutoCancel(true)
                .setSound(defaultSound);

        mNotifyMgr.notify(x, mBuilder.build());

    }


    public class hilo extends Thread{
        @Override
        public  void  run(){
            while (true){

                try {

                    DaoRecordatorio dao = new DaoRecordatorio(getApplicationContext());


                    final Calendar c= Calendar.getInstance();
                    String fecha = c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
                    String hora = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);


                    List<Recordatorio> lista ;
                    lista = dao.notificacionescumplidas(fecha);


                    Recordatorio recordatorio = new Recordatorio();
                    recordatorio.setId(1);
                    recordatorio.setTitulo("Titulo");
                    recordatorio.setDescripcion("Descripcion");
                    recordatorio.setFecha("Fecha");
                    recordatorio.setHora("Hora");

                    lista.add(recordatorio);



                    for (int i = 0; i < lista.size(); i++) {
                        if (lista.get(i).getHora().equalsIgnoreCase(hora) && ((c.get(Calendar.SECOND)) == 0)) {


                            Recordatorio recordatorio1 = new Recordatorio();
                            recordatorio1.setId(lista.get(i).getId());
                            recordatorio1.setTitulo(lista.get(i).getTitulo());
                            recordatorio1.setDescripcion(lista.get(i).getDescripcion());
                            recordatorio1.setFecha(lista.get(i).getFecha());
                            recordatorio1.setHora(lista.get(i).getHora());
                            listarecordatorios.add(recordatorio1);

                            btnNoti_click(lista.get(i).getTitulo(), lista.get(i).getDescripcion(),i);

                             }

                         }

                    Thread.sleep(1000);




                } catch (Exception e) {

                }
            }
        }
    }


}
