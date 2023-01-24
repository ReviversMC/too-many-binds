package com.github.reviversmc.toomanybinds.autocompletion;

import net.minecraft.client.option.KeyBinding;

public class VanillaKeybindSuggestions119 extends VanillaKeybindSuggestions1182 {
	@Override
	protected BindSuggestion newBindSuggestion(KeyBinding bind) {
		return new BindSuggestion119(bind);
	}
}
