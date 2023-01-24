package com.github.reviversmc.toomanybinds;

import net.minecraft.client.gui.widget.TextFieldWidget;

public class LauncherScreen117 extends LauncherScreen116 {
	@Override
	protected void closeScreen() {
		client.setScreen(null);
	}

	@Override
	protected void addTextField(TextFieldWidget textField) {
		addSelectableChild(textField);
	}
}
