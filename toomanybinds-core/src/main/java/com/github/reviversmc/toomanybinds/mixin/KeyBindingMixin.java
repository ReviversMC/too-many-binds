package com.github.reviversmc.toomanybinds.mixin;

import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.github.reviversmc.toomanybinds.mixinterface.KeyBindingMixinterface;

@Mixin(KeyBinding.class)
public class KeyBindingMixin implements KeyBindingMixinterface {
	@Shadow private int timesPressed;

	@Override
	public void toomanybinds_press() {
		timesPressed++;
	}
}
