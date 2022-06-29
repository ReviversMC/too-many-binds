package com.github.reviversmc.toomanybinds.autocompletion;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;

public class VanillaKeybindSuggestions116 extends VanillaKeybindSuggestions {

    @Override
    protected BindSuggestion newBindSuggestion(KeyBinding bind) {
        return new BindSuggestion116(bind);
    }

    @Override
    protected KeyBinding[] allKeys(GameOptions options) {
        return options.keysAll;
    }

}
