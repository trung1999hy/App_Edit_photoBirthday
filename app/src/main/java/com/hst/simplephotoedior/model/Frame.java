package com.hst.simplephotoedior.model;

public class Frame {
    private int frame;

    private boolean isNeedPoint;

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public boolean isNeedPoint() {return isNeedPoint;}

    public void setNeedPoint(boolean isNeedPoint) {this.isNeedPoint = isNeedPoint;}
}
