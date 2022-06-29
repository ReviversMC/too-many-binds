package com.github.reviversmc.toomanybinds;

import com.github.reviversmc.toomanybinds.autocompletion.VanillaKeybindSuggestions;
import com.github.reviversmc.toomanybinds.autocompletion.VanillaKeybindSuggestions1182;

import net.minecraft.client.MinecraftClient;

public class TooManyBinds1182 extends TooManyBinds117 {

	@Override
	protected VanillaKeybindSuggestions newVanillaKeyBindSuggestions() {
		return new VanillaKeybindSuggestions1182();
	}

}
