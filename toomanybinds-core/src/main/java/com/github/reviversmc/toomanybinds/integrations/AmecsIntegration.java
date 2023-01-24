package com.github.reviversmc.toomanybinds.integrations;

import de.siphalor.amecs.api.PriorityKeyBinding;
import net.minecraft.client.option.KeyBinding;

public class AmecsIntegration {
	public static void priorityPressIfApplicable(KeyBinding binding) {
		if (binding instanceof PriorityKeyBinding) {
			((PriorityKeyBinding) binding).onPressedPriority();
		}
	}
}
