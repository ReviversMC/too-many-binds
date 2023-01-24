package com.github.reviversmc.toomanybinds.autocompletion;

import java.util.function.Consumer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.ScreenshotUtils;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class BindSuggestion116 extends BindSuggestion {
	public BindSuggestion116(KeyBinding bind) {
		super(bind);
	}

	@Override
	protected Text newTranslatableText(String key) {
		return new TranslatableText(key);
	}

	@Override
	protected KeyBinding fullscreenKey(GameOptions options) {
		return options.keyFullscreen;
	}

	@Override
	protected void saveFullscreenState(GameOptions options, boolean fullscreen) {
		options.fullscreen = fullscreen;
	}

	@Override
	protected KeyBinding screenshotKey(GameOptions options) {
		return options.keyScreenshot;
	}

	@Override
	protected void takeScreenshot(MinecraftClient mc, Consumer<Text> consumer) {
		ScreenshotUtils.saveScreenshot(mc.runDirectory, mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight(), mc.getFramebuffer(), consumer);
	}
}
