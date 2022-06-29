package com.github.reviversmc.toomanybinds.autocompletion;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;

public class BindSuggestion119 extends BindSuggestion1182 {

    public BindSuggestion119(KeyBinding bind) {
        super(bind);
    }

    @Override
    protected Text newTranslatableText(String key) {
        return Text.translatable(key);
    }

    @Override
    protected void saveFullscreenState(GameOptions options, boolean fullscreen) {
        options.getFullscreen().setValue(fullscreen);
    }

}
