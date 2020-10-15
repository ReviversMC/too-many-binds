package dzwdz.toomanybinds;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

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
