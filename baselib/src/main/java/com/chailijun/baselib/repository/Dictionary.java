package com.chailijun.baselib.repository;

public class Dictionary {

    private float id;

    private String zi;

    private String py;
    private String wubi;
    private String bushou;

    private float bihua;

    private String pinyin;

    private String jijie;

    private String xiangjie;

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getZi() {
        return zi;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public String getWubi() {
        return wubi;
    }

    public void setWubi(String wubi) {
        this.wubi = wubi;
    }

    public String getBushou() {
        return bushou;
    }

    public void setBushou(String bushou) {
        this.bushou = bushou;
    }

    public float getBihua() {
        return bihua;
    }

    public void setBihua(float bihua) {
        this.bihua = bihua;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getJijie() {
        return jijie;
    }

    public void setJijie(String jijie) {
        this.jijie = jijie;
    }

    public String getXiangjie() {
        return xiangjie;
    }

    public void setXiangjie(String xiangjie) {
        this.xiangjie = xiangjie;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "id=" + id +
                ", zi='" + zi + '\'' +
                ", py='" + py + '\'' +
                ", wubi='" + wubi + '\'' +
                ", bushou='" + bushou + '\'' +
                ", bihua=" + bihua +
                ", pinyin='" + pinyin + '\'' +
                ", jijie='" + jijie + '\'' +
                ", xiangjie='" + xiangjie + '\'' +
                '}';
    }
}
