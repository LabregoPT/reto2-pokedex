package com.example.reto2.modelpokemonjson;

public class Type {

    private int slot;
    private _Type type;

    public Type() {
    }

    public Type(int slot, _Type type) {
        this.slot = slot;
        this.type = type;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public _Type getType() {
        return type;
    }

    public void setType(_Type type) {
        this.type = type;
    }
}
