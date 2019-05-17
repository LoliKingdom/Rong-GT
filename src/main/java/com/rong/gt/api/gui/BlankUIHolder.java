package com.rong.gt.api.gui;

public class BlankUIHolder implements IUIHolder {

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isRemote() {
        return false;
    }

    @Override
    public void markAsDirty() {
    }
}