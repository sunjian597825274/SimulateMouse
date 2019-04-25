package mousetvtest.sj.com.utils;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.net.InetAddress;

/**
 * Created by sunjian on 2019/4/22.
 */

public class WifiUtils {
    public static String getWifiIp(Context myContext) {
        WifiManager wifiMgr = (WifiManager) myContext.getSystemService(Context.WIFI_SERVICE);
        int paramInt = wifiMgr.getConnectionInfo().getIpAddress();
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "."
                + (0xFF & paramInt >> 24);
    }
}
