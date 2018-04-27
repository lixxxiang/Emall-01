package com.example.emall_ec.main.index.dailypic.video.data;

public class Gps {

    public double lat;
    public double lon;

    public Gps(double lon, double lat) {
        this.lat = lat;
        this.lon = lon;
    }

    public void print() {
        System.out.println(this.lon + "," + this.lat);
    }
}
