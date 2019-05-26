package com.rong.rt.api.utils;

import java.util.function.Consumer;

import net.minecraft.client.gui.GuiPageButtonList.GuiResponder;

public class MCGuiUtil {

    public static GuiResponder createTextFieldResponder(Consumer<String> onChanged) {
        return new GuiResponder() {
            @Override
            public void setEntryValue(int id, boolean value) {
            }
            @Override
            public void setEntryValue(int id, float value) {
            }
            @Override
            public void setEntryValue(int id, String value) {
                onChanged.accept(value);
            }
        };
    }


}