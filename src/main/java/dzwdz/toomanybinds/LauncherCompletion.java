package dzwdz.toomanybinds;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;

import java.util.*;

public class LauncherCompletion {
    private static final Set<String> blacklist = new HashSet<String>(Arrays.asList(
            // pointless
            "key.use",

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

    private List<BindSuggestion> all;
    private List<BindSuggestion> currentSuggestions;

    public LauncherCompletion() {
        all = new ArrayList<>();
        currentSuggestions = new ArrayList<>();
        for (KeyBinding bind : MinecraftClient.getInstance().options.keysAll) {
            if ((bind.isUnbound() && TooManyBinds.config.hideBoundKeys) || !blacklist.contains(bind.getTranslationKey()))
                all.add(new BindSuggestion(bind));
        }
    }

    public void updateSuggestions(String search) {
        currentSuggestions.clear();
        if (search.isEmpty()) return;
        String[] terms = search.toLowerCase().split(" ");
        for (BindSuggestion bind : all) {
            if (bind.matches(terms)) {
                currentSuggestions.add(bind);
                if (currentSuggestions.size() >= 5) return;
            }
        }
    }

    public List<BindSuggestion> getSuggestions() {
        return currentSuggestions;
    }
}
