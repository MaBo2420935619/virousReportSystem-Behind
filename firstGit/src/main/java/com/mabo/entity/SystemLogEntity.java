package com.mabo.entity;

public class SystemLogEntity {

    private String ip;
    private int port;
    private String requestype;
    private String issuccess;
    private long time;

    public SystemLogEntity() {

    }

    public SystemLogEntity(String ip, int port, String requestype, String issuccess, long time) {
        this.ip = ip;
        this.port = port;
        this.requestype = requestype;
        this.issuccess = issuccess;
        this.time = time;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public void setRequestype(String requestype) {
        this.requestype = requestype;
    }

    public void setIssuccess(String issuccess) {
        this.issuccess = issuccess;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public String getRequestype() {
        return requestype;
    }

    public String getIssuccess() {
        return issuccess;
    }

    public long getTime() {
        return time;
    }

}
