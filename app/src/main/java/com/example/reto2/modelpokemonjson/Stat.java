package com.example.reto2.modelpokemonjson;

public class Stat {
    private int base_stat;
    private Stat_ stat;

    public Stat() {
    }

    public Stat(int base_stat, Stat_ stat) {
        this.base_stat = base_stat;
        this.stat = stat;
    }

    public int getBase_stat() {
        return base_stat;
    }

    public void setBase_stat(int base_stat) {
        this.base_stat = base_stat;
    }

    public Stat_ getStat() {
        return stat;
    }

    public void setStat(Stat_ stat) {
        this.stat = stat;
    }
}
