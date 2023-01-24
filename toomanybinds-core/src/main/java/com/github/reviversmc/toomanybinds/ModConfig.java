package com.github.reviversmc.toomanybinds;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "toomanybinds")
public class ModConfig implements ConfigData {
	public boolean hideBoundKeys = false;
	public int maxSuggestions = 5;

	@ConfigEntry.Gui.Excluded
	public double launcherX = 0;
	@ConfigEntry.Gui.Excluded
	public double launcherY = 0;

	public double bgOpacity = 0.7;
}
