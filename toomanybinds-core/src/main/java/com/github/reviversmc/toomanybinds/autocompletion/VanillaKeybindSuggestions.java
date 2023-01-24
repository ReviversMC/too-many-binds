package com.github.reviversmc.toomanybinds.autocompletion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;

import com.github.reviversmc.toomanybinds.TooManyBinds;

public abstract class VanillaKeybindSuggestions implements SuggestionProvider {
	private static final Set<String> blacklist = new HashSet<String>(Arrays.asList(
			// pointless
			"key.use",
			"key.toomanybinds.launcher",
			"key.toomanybinds.favorite",

			// don't work
			"key.attack",
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

	protected abstract KeyBinding[] allKeys(GameOptions options);

	public void addEntries(List<BindSuggestion> binds) {
		for (KeyBinding bind : allKeys(MinecraftClient.getInstance().options)) {
			if ((bind.isUnbound() || !TooManyBinds.config.hideBoundKeys)
					&& !blacklist.contains(bind.getTranslationKey())) {
				binds.add(newBindSuggestion(bind));
			}
		}
	}

	protected abstract BindSuggestion newBindSuggestion(KeyBinding bind);
}
