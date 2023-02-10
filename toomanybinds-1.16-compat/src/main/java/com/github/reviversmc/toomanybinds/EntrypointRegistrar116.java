package com.github.reviversmc.toomanybinds;

public class EntrypointRegistrar116 extends EntrypointRegistrarBase {
	@Override
	public void onInitialize() {
		if (isCompatible("1.16", "1.16.5")) {
			EntrypointLauncher.setVersionSpecificInitializer(new TooManyBinds116());
		}
	}
}
