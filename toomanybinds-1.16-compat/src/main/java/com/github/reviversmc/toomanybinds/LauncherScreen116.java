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
	protected void setRepeatKeyboardEvents(boolean repeatEvents) {
		client.keyboard.setRepeatEvents(repeatEvents);
	}
}
