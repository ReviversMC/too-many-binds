package com.github.reviversmc.toomanybinds.autocompletion;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;

public class VanillaKeybindSuggestions1182 extends VanillaKeybindSuggestions117 {
	@Override
	protected BindSuggestion newBindSuggestion(KeyBinding bind) {
		return new BindSuggestion1182(bind);
	}

	@Override
	protected KeyBinding[] allKeys(GameOptions options) {
		return options.allKeys;
	}
}
