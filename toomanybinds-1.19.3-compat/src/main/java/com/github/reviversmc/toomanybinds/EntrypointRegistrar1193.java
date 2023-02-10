package com.github.reviversmc.toomanybinds;

public class EntrypointRegistrar1193 extends EntrypointRegistrarBase {
	@Override
	public void onInitialize() {
		if (isCompatible("1.19.3", INFINITE)) {
			EntrypointLauncher.setVersionSpecificInitializer(new TooManyBinds1193());
		}
	}
}
