package dzwdz.toomanybinds;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LauncherCompletion {
    private Map<String, KeyBinding> allKeys;
    private List<Map.Entry<String, KeyBinding>> suggestions;

    public LauncherCompletion() {
        allKeys = new HashMap<>();
        suggestions = new ArrayList<>();
        for (KeyBinding bind : MinecraftClient.getInstance().options.keysAll) {
            allKeys.put(new TranslatableText(bind.getTranslationKey()).getString(), bind);
        }
    }

    public void updateSuggestions(String search) {
        suggestions.clear();
        if (search.isEmpty()) return;
        for (Map.Entry<String, KeyBinding> bind : allKeys.entrySet()) {
            // todo this is garbage
            if (bind.getKey().toLowerCase().contains(search.toLowerCase())) {
                suggestions.add(bind);
                if (suggestions.size() >= 5) return;
            }
        }
    }

    public List<Map.Entry<String, KeyBinding>> getSuggestions() {
        return suggestions;
    }
}
