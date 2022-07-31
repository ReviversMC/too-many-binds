package com.github.reviversmc.toomanybinds;

import com.github.reviversmc.toomanybinds.autocompletion.VanillaKeybindSuggestions;
import com.github.reviversmc.toomanybinds.autocompletion.VanillaKeybindSuggestions117;

import net.minecraft.client.MinecraftClient;

public class TooManyBinds117 extends TooManyBinds116 {

	@Override
	protected VanillaKeybindSuggestions newVanillaKeyBindSuggestions() {
		return new VanillaKeybindSuggestions117();
	}

	@Override
	protected void openNewLauncherScreen(MinecraftClient client) {
		client.setScreen(new LauncherScreen117());
	}

}
