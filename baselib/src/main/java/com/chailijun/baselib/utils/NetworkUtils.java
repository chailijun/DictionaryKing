package com.chailijun.baselib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class NetworkUtils {

    private boolean isThereInternetConnection(Context context) {
        boolean isConnected = false;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());
        }
        return isConnected;
    }

    /**
     * 判断网络是不是手机网络，非wifi
     *
     * @param context Context
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isMobileNetwork(Context context) {
        return isNetworkAvailable(context) && (ConnectivityManager.TYPE_MOBILE == getNetworkType(context));
    }

    /**
     * Returns whether the network is available
     *
     * @param context Context
     * @return 网络是否可用
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

//    public static boolean isNetworkConnect(Context getContext) {
//        ConnectivityManager connMgr = (ConnectivityManager) getContext
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        boolean isWifiConn = networkInfo.isConnected();
//        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        boolean isMobileConn = networkInfo.isConnected();
//        return isWifiConn || isMobileConn;
//    }

    /**
     * 获取网络类型
     *
     * @param context Context
     * @return 网络类型
     * @see [类、类#方法、类#成员]
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {

        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        return info[i].getType();
                    }
                }
            }
        }

        return -1;
    }

    public static String getIPonWiFi(Context context) {
        Context applicationContext = context.getApplicationContext();
        WifiManager wm = (WifiManager) applicationContext.getSystemService(Context.WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }
}
