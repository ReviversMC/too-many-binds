package com.github.reviversmc.toomanybinds;

public class EntrypointRegistrar1182 extends EntrypointRegistrarBase {
	@Override
	public void onInitialize() {
		if (isCompatible("1.18.2", "1.18.2")) {
			EntrypointLauncher.setVersionSpecificInitializer(new TooManyBinds1182());
		}
	}
}
