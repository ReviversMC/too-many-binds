package com.github.reviversmc.toomanybinds;

public class EntrypointRegistrar117 extends EntrypointRegistrarBase {
	@Override
	public void onInitialize() {
		if (isCompatible("1.17", "1.18.1")) {
			EntrypointLauncher.setVersionSpecificInitializer(new TooManyBinds117());
		}
	}
}
