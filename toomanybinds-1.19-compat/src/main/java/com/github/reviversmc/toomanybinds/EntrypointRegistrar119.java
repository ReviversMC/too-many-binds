package com.github.reviversmc.toomanybinds;

public class EntrypointRegistrar119 extends EntrypointRegistrarBase {
	@Override
	public void onInitialize() {
		if (isCompatible("1.19", "1.19.2")) {
			EntrypointLauncher.setVersionSpecificInitializer(new TooManyBinds119());
		}
	}
}
