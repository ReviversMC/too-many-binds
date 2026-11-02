package com.github.reviversmc.toomanybinds;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class TooManyBinds implements ModInitializer {
	public static ModConfig config;
	public static KeyBinding launcherKey;
	public static KeyBinding favoriteKey;
	public static final KeyBinding.Category CATEGORY = KeyBinding.Category.create(Identifier.of("toomanybinds", "category"));

	@Override
	public void onInitialize() {
		AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

		launcherKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.toomanybinds.launcher",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_H,
				CATEGORY));
		favoriteKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.toomanybinds.favorite",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_F4,
				CATEGORY));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (launcherKey.wasPressed()) client.setScreen(new LauncherScreen());
		});
	}
}
