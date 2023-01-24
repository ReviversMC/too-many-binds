package com.github.reviversmc.toomanybinds;

import net.minecraft.client.MinecraftClient;

import com.github.reviversmc.toomanybinds.autocompletion.VanillaKeybindSuggestions;
import com.github.reviversmc.toomanybinds.autocompletion.VanillaKeybindSuggestions116;

public class TooManyBinds116 extends TooManyBinds {
	@Override
	protected VanillaKeybindSuggestions newVanillaKeyBindSuggestions() {
		return new VanillaKeybindSuggestions116();
	}

	@Override
	protected void openNewLauncherScreen(MinecraftClient client) {
		client.openScreen(new LauncherScreen116());
	}
}
