package dzwdz.toomanybinds.autocompletion;

import dzwdz.toomanybinds.TooManyBinds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VanillaKeybindSuggestions implements SuggestionProvider {
    private static final Set<String> blacklist = new HashSet<String>(Arrays.asList(
            // pointless
            "key.use",
            "key.toomanybinds.launcher",
            "key.toomanybinds.favorite",

            // don't work
            "key.attack",
            "key.forward",
            "key.left",
            "key.right",
            "key.back",
            "key.sneak",
            "key.sprint",
            "key.jump",

            // require another key to be pressed ; could work with some changes
            "key.saveToolbarActivator",
            "key.loadToolbarActivator"
    ));

    public void addEntries(List<BindSuggestion> binds) {
        for (KeyBinding bind : MinecraftClient.getInstance().options.keysAll) {
            if ((bind.isUnbound() || !TooManyBinds.config.hideBoundKeys) && !blacklist.contains(bind.getTranslationKey()))
                binds.add(new BindSuggestion(bind));
        }
    }
}
