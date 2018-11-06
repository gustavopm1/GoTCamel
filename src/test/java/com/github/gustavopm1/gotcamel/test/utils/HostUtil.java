package com.github.gustavopm1.gotcamel.test.utils;

public class HostUtil {

    public static String getHost() {
        java.lang.management.RuntimeMXBean runtime =
                java.lang.management.ManagementFactory.getRuntimeMXBean();
        String host = runtime.getName().split("@")[1];

        return host.replaceAll("[^a-zA-Z0-9]", "");

    }
    public static int getHostAsHash() {
        return getHost().hashCode();
    }

    public static String appendHostName(String value){
        return getHost()+value;
    }

}