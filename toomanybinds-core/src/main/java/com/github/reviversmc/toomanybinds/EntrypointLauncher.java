package com.github.reviversmc.toomanybinds;

import net.fabricmc.api.ModInitializer;

public class EntrypointLauncher implements ModInitializer {
	private static ModInitializer versionSpecificInitializer;
	private static boolean waitingForInitializerToBeSet;

	public static void setVersionSpecificInitializer(ModInitializer initializer) {
		versionSpecificInitializer = initializer;

		if (waitingForInitializerToBeSet) {
			waitingForInitializerToBeSet = false;
			versionSpecificInitializer.onInitialize();
		}
	}

	@Override
	public void onInitialize() {
		if (versionSpecificInitializer == null) {
			waitingForInitializerToBeSet = true;
		} else {
			versionSpecificInitializer.onInitialize();
		}
	}
}
