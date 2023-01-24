package com.github.reviversmc.toomanybinds.autocompletion;

import java.util.function.Consumer;

import de.siphalor.amecs.api.PriorityKeyBinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;

import com.github.reviversmc.toomanybinds.mixinterface.KeyBindingMixinterface;

public abstract class BindSuggestion {
	public Text name;
	public Text category;
	public KeyBinding bind;
	private String searchable;
	public boolean favorite = false;

	public BindSuggestion(KeyBinding bind) {
		this.bind = bind;
		name = newTranslatableText(bind.getTranslationKey());
		category = newTranslatableText(bind.getCategory());
		searchable = (name.getString() + " " + category.getString()).toLowerCase();
	}

	protected abstract Text newTranslatableText(String key);

	public boolean matches(String[] searchTerms) {
		for (String term : searchTerms) {
			if (!searchable.contains(term)) return false;
		}

		return true;
	}

	public void execute() {
		MinecraftClient mc = MinecraftClient.getInstance();
		GameOptions options = mc.options;

		LauncherCompletion.addToHistory(getId());

		// workarounds for keybinds that are handled in dumb, incompatible ways
		if (bind == fullscreenKey(options)) {
			mc.getWindow().toggleFullscreen();
			saveFullscreenState(mc.options, mc.getWindow().isFullscreen());
			mc.options.write();
		} else if (bind == screenshotKey(options)) {
			takeScreenshot(mc, (text) -> {
				mc.execute(() -> {
					mc.inGameHud.getChatHud().addMessage(text);
				});
			});
		} else {
			((KeyBindingMixinterface) bind).toomanybinds_press();
			// amecs compat
			bind.setPressed(true);
			bind.setPressed(false);
			if (bind instanceof PriorityKeyBinding) ((PriorityKeyBinding) bind).onPressedPriority();
		}
	}

	protected abstract KeyBinding fullscreenKey(GameOptions options);
	protected abstract void saveFullscreenState(GameOptions options, boolean fullscreen);
	protected abstract KeyBinding screenshotKey(GameOptions options);
	protected abstract void takeScreenshot(MinecraftClient mc, Consumer<Text> messageReceiver);

	public String getId() {
		return bind.getTranslationKey();
	}
}
