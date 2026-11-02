package com.github.reviversmc.toomanybinds.autocompletion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

import com.github.reviversmc.toomanybinds.TooManyBinds;

public class VanillaKeybindSuggestions implements SuggestionProvider {
	// These keybinds either require other keybinds to be pressed (meaning that they don't work in a launcher screen),
	// or are already handled in different ways.
	public static Set<String> blacklist = new HashSet<>(Arrays.asList(
			// movement ; handled in better ways
			"key.forward",
			"key.left",
			"key.right",
			"key.back",
			"key.sneak",
			"key.sprint",
			"key.jump",

			// require another key to be pressed ; could work with some changes
			"key.saveToolbarActivator",
			"key.loadToolbarActivator"
	));

	@SuppressWarnings("resource")
	public void addEntries(List<BindSuggestion> binds) {
		for (KeyBinding bind : MinecraftClient.getInstance().options.allKeys) {
			if ((bind.isUnbound() || !TooManyBinds.config.hideBoundKeys)
					&& !blacklist.contains(bind.getId())) {
				binds.add(new BindSuggestion(bind));
			}
		}
	}
}
