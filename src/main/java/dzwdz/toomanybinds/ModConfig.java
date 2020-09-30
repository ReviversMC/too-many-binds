package dzwdz.toomanybinds;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "toomanybinds")
public class ModConfig implements ConfigData {
    public boolean hideBoundKeys = false;
    public int maxSuggestions = 5;
}
