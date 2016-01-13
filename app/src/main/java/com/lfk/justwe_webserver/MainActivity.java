package com.lfk.justwe_webserver;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lfk.justwe_webserver.WebServer.OnLogResult;
import com.lfk.justwe_webserver.WebServer.WebServer;

public class MainActivity extends AppCompatActivity implements OnLogResult {
    private WebServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        server = new WebServer(MainActivity.this, this);
        server.initWebService();

//        bindService(new Intent(MainActivity.this, WebServerService.class),
//                serviceConnection,
//                Context.BIND_AUTO_CREATE
//        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                server.startWebService();
            }
        });
    }


//    private ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
////            webServerService = ((WebServerService.LocalBinder) service).getService();
//            Log.e("lll", "getweb");
////            if (logResult != null)
////                logResult.OnResult(WebServerDefault.WebServerServiceConnected);
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
////            webServerService = null;
//            Log.e("lll", "error");
////            if (logResult != null)
////                logResult.OnResult(WebServerDefault.WebServerServiecDisconnected);
//        }
//    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnResult(String log) {
        Log.d("log", log);
    }

    @Override
    public void OnError(String error) {
        Log.e("error", error);
    }
}
