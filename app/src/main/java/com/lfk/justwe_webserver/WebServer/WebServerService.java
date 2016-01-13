package com.lfk.justwe_webserver.WebServer;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;

import com.lfk.justwe_webserver.R;

/**
 * Created by liufengkai on 16/1/6.
 */
public class WebServerService extends Service {
    private OnLogResult logResult;
    private NotificationManager notificationManager;
    private Notification notification;
    private final IBinder mBinder = new LocalBinder();
    private Server webServers;
    private static Activity engine;
    private PendingIntent contentIntent;
    private boolean IsRunning;

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

    }

    public static void init(Activity engine) {
        WebServerService.engine = engine;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void showNotification() {

        startForeground(0, notification);
    }

    private void updateNotification(String noti) {
        if (notification == null) {
            contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, engine.getClass()), 0);
            Notification.Builder builder = new Notification.Builder(engine)
                    .setContentTitle("WebServer")
                    .setContentText(noti)
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis());
            notification = builder.getNotification();
            notificationManager.notify(0, notification);
        } else {
            notificationManager.notify(0, notification);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public class LocalBinder extends Binder {
        WebServerService getService() {
            return WebServerService.this;
        }
    }

    public void startServer(OnLogResult logResult, int port) {
        this.logResult = logResult;
        // running
        setIsRunning(true);

        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        WebServerDefault.setWebServerIp(WebServerDefault.intToIp(wifiInfo.getIpAddress()));
        // if not in wifi
        if (wifiInfo.getSupplicantState() != SupplicantState.COMPLETED) {
            this.logResult.OnError("Please connect to a WIFI-network.");
            try {
                throw new Exception("Please connect to a WIFI-network.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        webServers = new Server();
        webServers.start();
    }

    public void stopServer() {

    }

    public void setIsRunning(boolean isRunning) {
        IsRunning = isRunning;
    }
}
