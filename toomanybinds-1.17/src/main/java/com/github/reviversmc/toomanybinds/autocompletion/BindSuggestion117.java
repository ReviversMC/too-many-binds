package com.github.reviversmc.toomanybinds.autocompletion;

import java.util.function.Consumer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.text.Text;

public class BindSuggestion117 extends BindSuggestion116 {

    public BindSuggestion117(KeyBinding bind) {
        super(bind);
    }

    @Override
    protected void takeScreenshot(MinecraftClient mc, Consumer<Text> consumer) {
        ScreenshotRecorder.saveScreenshot(mc.runDirectory, mc.getFramebuffer(), consumer);
    }

}
