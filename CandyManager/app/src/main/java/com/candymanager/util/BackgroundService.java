package com.candymanager.util;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.*;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.*;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.widget.Toast;

import com.candymanager.R;
import com.candymanager.configuracao.NotificacaoPedidosSharedPreference;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.pedidos.PedidoController;
import com.candymanager.pedidos.PedidoDAO;
import com.candymanager.pedidos.PedidoModel;

import java.util.List;

public class BackgroundService extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    private PedidoDAO pedidoDAO;
    private NotificacaoPedidosSharedPreference notificacaoPedidosSharedPreference;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        pedidoDAO = new PedidoDAO(this);
        notificacaoPedidosSharedPreference = new NotificacaoPedidosSharedPreference(this);

        handler = new Handler();
        runnable = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void run() {

                if (notificacaoPedidosSharedPreference.isEnabled()) {
                    List<PedidoModel> list = pedidoDAO.getListaDiaDeHoje();
                    if (list.size() > 0) {
                        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


                        NotificationCompat.Builder builder = new NotificationCompat.Builder(BackgroundService.this);
                        builder
                                .setContentTitle("Você tem pedidos para hoje!")
                                .setContentText("São " + list.size() + " pedido(s)")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                .setSound(soundUri);

                        Intent intent = new Intent(BackgroundService.this, PedidoController.class);
                        @SuppressLint("WrongConstant") PendingIntent pi = PendingIntent.getActivity(BackgroundService.this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
                        builder.setContentIntent(pi);
                        NotificationManager mNotificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(0, builder.build());
                    }
                }


                //Toast.makeText(context, "Service is still running " + pedidoDAO.getListaDiaDeHoje().size(), Toast.LENGTH_LONG).show();
                handler.postDelayed(runnable, 28800000);
            }
        };

        handler.postDelayed(runnable, 10000);
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
    }

    @Override
    public void onStart(Intent intent, int startid) {
    }
}