package org.revo.torrentclient;

public class Torrent {
    private String murl;

    public Torrent() {
    }

    public Torrent(String murl) {
        this.murl = murl;
    }

    public String getMurl() {
        return murl;
    }

    public void setMurl(String murl) {
        this.murl = murl;
    }
}
