package com.github.reviversmc.toomanybinds.autocompletion;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;

public class BindSuggestion1182 extends BindSuggestion117 {
	public BindSuggestion1182(KeyBinding bind) {
		super(bind);
	}

	@Override
	protected KeyBinding fullscreenKey(GameOptions options) {
		return options.fullscreenKey;
	}

	@Override
	protected KeyBinding screenshotKey(GameOptions options) {
		return options.screenshotKey;
	}
}
