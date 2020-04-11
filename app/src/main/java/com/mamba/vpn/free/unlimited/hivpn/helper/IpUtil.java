package com.mamba.vpn.free.unlimited.hivpn.helper;

/**
 * created by edison 2020/4/9
 */
public class IpUtil {

    public static String getIpAddress(int ipAddress){
        String ip = (ipAddress & 0xff) + "." + (ipAddress>>8 & 0xff) + "." + (ipAddress>>16 & 0xff) + "." + (ipAddress >> 24 & 0xff);
        return ip;
    }

}
