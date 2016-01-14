package com.lfk.justwe_webserver;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lfk.justwe_webserver.WebServer.OnLogResult;
import com.lfk.justwe_webserver.WebServer.OnPostData;
import com.lfk.justwe_webserver.WebServer.OnWebFileResult;
import com.lfk.justwe_webserver.WebServer.OnWebStringResult;
import com.lfk.justwe_webserver.WebServer.WebServer;
import com.lfk.justweengine.Utils.logger.Logger;

import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnLogResult {
    private WebServer server;
    private TextView textView;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.main_log);
        scrollView = (ScrollView) findViewById(R.id.main_scroll);

        server = new WebServer(MainActivity.this, this);
        server.initWebService();


        server.apply("/lfk", new OnWebStringResult() {
            @Override
            public String OnResult() {
                return "=======";
            }
        });

        server.apply("/main", new OnWebFileResult() {
            @Override
            public File returnFile() {
                return new File(Environment.getExternalStorageDirectory()
                        + "/androidwebserver/welcome.html");
            }
        });

        server.apply("/");

        server.apply("/lfkdsk", new OnPostData() {
            @Override
            public String OnPostData(HashMap<String, String> hashMap) {
                String S = hashMap.get("LFKDSK");
                Logger.e(S);
                return "fuck you";
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                server.startWebService();
            }
        });
    }


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
        Log.e("log", log);
        textView.append(log + "\n");
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    @Override
    public void OnError(String error) {
        Log.e("error", error);

    }

}
