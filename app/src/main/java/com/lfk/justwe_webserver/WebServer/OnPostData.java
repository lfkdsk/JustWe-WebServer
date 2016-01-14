package com.lfk.justwe_webserver.WebServer;

import java.util.HashMap;

/**
 * Created by liufengkai on 16/1/14.
 */
public interface OnPostData extends OnWebResult {
    String OnPostData(HashMap<String, String> hashMap);
}
