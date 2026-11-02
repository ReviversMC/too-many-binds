package com.github.reviversmc.toomanybinds.integrations;

import java.lang.reflect.Method;
import net.minecraft.client.option.KeyBinding;

public class AmecsIntegration {
	public static void priorityPressIfApplicable(KeyBinding binding) {
		try {
			Class<?> clazz = Class.forName("de.siphalor.amecs.api.PriorityKeyBinding");
			if (clazz.isInstance(binding)) {
				Method method = clazz.getMethod("onPressedPriority");
				method.invoke(binding);
			}
		} catch (Throwable ignored) {
		}
	}
}
