package com.github.reviversmc.toomanybinds;

import net.minecraft.client.gui.widget.TextFieldWidget;

public class LauncherScreen116 extends LauncherScreen {

    @Override
    protected void closeScreen() {
        client.openScreen(null);
    }

    @Override
    protected void addTextField(TextFieldWidget textField) {
        children.add(textField);
    }

    @Override
    protected void init() {
        client.keyboard.setRepeatEvents(true);
        super.init(client, width, height);
    }

    @Override
    public void removed() {
        client.keyboard.setRepeatEvents(false);
    }
}
