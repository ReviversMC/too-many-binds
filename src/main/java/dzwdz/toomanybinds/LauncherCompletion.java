package dzwdz.toomanybinds;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LauncherCompletion {
    private static final Set<String> blacklist = new HashSet<String>(Arrays.asList(
            // pointless
            "key.use",
            "key.toomanybinds.launcher",

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

    public static List<String> history = new LinkedList<>();

    private List<BindSuggestion> all;
    private List<BindSuggestion> currentSuggestions;

    public LauncherCompletion() {
        all = new LinkedList<>();
        currentSuggestions = new ArrayList<>();
        for (KeyBinding bind : MinecraftClient.getInstance().options.keysAll) {
            if ((bind.isUnbound() && TooManyBinds.config.hideBoundKeys) || !blacklist.contains(bind.getTranslationKey()))
                all.add(new BindSuggestion(bind));
        }
        for (String s : Lists.reverse(history)) {
            Iterator<BindSuggestion> itr = all.iterator();
            while (itr.hasNext()) {
                BindSuggestion bs = itr.next();
                if (bs.getId().equals(s)) {
                    itr.remove();
                    all.add(0, bs);
                    break;
                }
            }
        }
    }

    public void updateSuggestions(String search) {
        currentSuggestions.clear();
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

    public static void addToHistory(String string) {
        history.remove(string);
        history.add(0, string);
    }

    private static File getHistoryFile() {
        return new File(MinecraftClient.getInstance().runDirectory, "toomanybinds_history.txt");
    }

    public static void loadHistory() {
        history.clear();
        try {
            Files.newReader(getHistoryFile(), Charsets.UTF_8).lines().forEach(history::add);
        } catch (Throwable ignored) {}
    }

    public static void saveHistory() {
        try {
            PrintWriter printWriter = new PrintWriter(
                    new OutputStreamWriter(new FileOutputStream(getHistoryFile()), StandardCharsets.UTF_8)
            );
            for (String s : history)
                printWriter.println(s);
            printWriter.close();
        } catch (Throwable ignored) {}
    }
}
