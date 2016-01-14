package com.lfk.justwe_webserver.WebServer;

import java.io.File;

/**
 * Created by liufengkai on 16/1/6.
 */
public interface OnWebFileResult extends OnWebResult {
    File returnFile();
}
