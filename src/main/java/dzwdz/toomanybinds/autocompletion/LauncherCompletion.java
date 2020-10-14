package dzwdz.toomanybinds.autocompletion;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LauncherCompletion {
    public static List<SuggestionProvider> suggestionProviders = new ArrayList<>();

    public static List<String> history = new LinkedList<>();

    private List<BindSuggestion> all;
    private List<BindSuggestion> currentSuggestions;

    public LauncherCompletion() {
        all = new LinkedList<>();
        currentSuggestions = new ArrayList<>();

        for (SuggestionProvider sp : suggestionProviders)
            sp.addEntries(all);

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
        // todo : optimize
        currentSuggestions.clear();
        String[] terms = search.toLowerCase().split(" ");
        for (BindSuggestion bind : all) {
            if (bind.matches(terms)) {
                currentSuggestions.add(bind);
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
