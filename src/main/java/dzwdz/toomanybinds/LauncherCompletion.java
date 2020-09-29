package dzwdz.toomanybinds;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;

import java.util.ArrayList;
import java.util.List;

public class LauncherCompletion {
    private List<BindSuggestion> all;
    private List<BindSuggestion> currentSuggestions;

    public LauncherCompletion() {
        all = new ArrayList<>();
        currentSuggestions = new ArrayList<>();
        for (KeyBinding bind : MinecraftClient.getInstance().options.keysAll) {
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
