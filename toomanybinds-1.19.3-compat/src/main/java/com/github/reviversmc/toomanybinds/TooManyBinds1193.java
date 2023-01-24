package com.github.reviversmc.toomanybinds;

import net.minecraft.client.MinecraftClient;

import com.github.reviversmc.toomanybinds.autocompletion.VanillaKeybindSuggestions;
import com.github.reviversmc.toomanybinds.autocompletion.VanillaKeybindSuggestions119;

public class TooManyBinds1193 extends TooManyBinds119 {

	@Override
	protected VanillaKeybindSuggestions newVanillaKeyBindSuggestions() {
		return new VanillaKeybindSuggestions119();
	}

    @Override
    protected void openNewLauncherScreen(MinecraftClient client) {
        client.setScreen(new LauncherScreen1193());
    }

}
