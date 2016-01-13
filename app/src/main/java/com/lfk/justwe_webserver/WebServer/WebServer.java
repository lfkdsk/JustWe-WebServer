package com.lfk.justwe_webserver.WebServer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.HashMap;

/**
 * WebServer
 *
 * @author liufengkai
 *         Created by liufengkai on 16/1/6.
 */
public class WebServer {
    private Activity engine;
    private static HashMap<String, OnWebResult> webServerRule;
    private OnLogResult logResult;
    private WebServerService webServerService;
    private Integer webPort = null;
    private ServiceConnection serviceConnection;

    public WebServer(Activity engine) {
        this.engine = engine;
        init();
    }

    public WebServer(Activity engine, OnLogResult logResult) {
        this.engine = engine;
        this.logResult = logResult;
        init();
    }

    public WebServer(Activity engine, OnLogResult logResult, int webPort) {
        this.engine = engine;
        this.logResult = logResult;
        this.webPort = webPort;
        init();
    }

    private void init() {
        webServerRule = new HashMap<>();

        WebServerService.init(engine);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                webServerService = ((WebServerService.LocalBinder) service).getService();
                Log.e("lll", "getweb");
                if (logResult != null)
                    logResult.OnResult(WebServerDefault.WebServerServiceConnected);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                webServerService = null;
                Log.e("lll", "error");
                if (logResult != null)
                    logResult.OnResult(WebServerDefault.WebServerServiecDisconnected);
            }
        };

    }


    public void startWebService() {
        Log.e("lll", "first");
        if (webServerService != null) {
            Log.e("lll", "open");
            webServerService.startServer(logResult,
                    (webPort == null) ? WebServerDefault.WebDefaultPort : webPort);
        }
    }

    public void stopWebService() {
        if (webServerService != null) {
            webServerService.stopServer();
        }
    }

    public void initWebService() {
        // 绑定Service
        engine.bindService(new Intent(engine, WebServerService.class),
                serviceConnection,
                Context.BIND_AUTO_CREATE
        );
    }

    public void callOffWebService() {
        engine.unbindService(serviceConnection);
    }

    public static void apply(String rule, OnWebResult result) {
        webServerRule.put(rule, result);
    }

    public static OnWebResult getRule(String rule) {
        return webServerRule.get(rule);
    }

    public void setLogResult(OnLogResult logResult) {
        this.logResult = logResult;
    }


}
