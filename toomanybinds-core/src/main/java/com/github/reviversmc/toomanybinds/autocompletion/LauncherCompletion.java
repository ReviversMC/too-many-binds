package com.github.reviversmc.toomanybinds.autocompletion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import net.minecraft.client.MinecraftClient;

public class LauncherCompletion {
	public static List<SuggestionProvider> suggestionProviders = new ArrayList<>();

	public static List<String> history = new LinkedList<>();
	public static List<String> favorites = new LinkedList<>();

	private List<BindSuggestion> all;
	private List<BindSuggestion> currentSuggestions;

	public LauncherCompletion() {
		all = new LinkedList<>();
		currentSuggestions = new ArrayList<>();

		for (SuggestionProvider sp : suggestionProviders) {
			sp.addEntries(all);
		}

		List<BindSuggestion> tempFavorites = new LinkedList<>();

		for (String s : Lists.reverse(history)) {
			Iterator<BindSuggestion> itr = all.iterator();

			while (itr.hasNext()) {
				BindSuggestion bs = itr.next();

				if (bs.getId().equals(s)) {
					itr.remove();

					if (favorites.contains(s)) {
						bs.favorite = true;
						tempFavorites.add(bs);
					} else {
						all.add(0, bs);
					}

					break;
				}
			}
		}

		all.addAll(0, tempFavorites);
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

	public static void toggleFavorite(BindSuggestion bs) {
		bs.favorite ^= true;

		if (bs.favorite) {
			favorites.add(bs.getId());
			history.add(bs.getId());
		} else {
			favorites.remove(bs.getId());
		}
	}

	public static void addToHistory(String string) {
		history.remove(string);
		history.add(0, string);
	}

	private static File getFile(String suffix) {
		return new File(MinecraftClient.getInstance().runDirectory, "toomanybinds_" + suffix + ".txt");
	}

	public static void loadData() {
		history.clear();
		favorites.clear();

		try {
			Files.newReader(getFile("history"), Charsets.UTF_8).lines().forEach(history::add);
			Files.newReader(getFile("favorites"), Charsets.UTF_8).lines().forEach(favorites::add);
		} catch (Throwable e) {
			// ignored
		}
	}

	public static void saveData() {
		try {
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(getFile("history")), StandardCharsets.UTF_8));
			for (String s : history) {
				printWriter.println(s);
			}

			printWriter.close();

			printWriter = new PrintWriter(new OutputStreamWriter(
						new FileOutputStream(getFile("favorites")), StandardCharsets.UTF_8));
			for (String s : favorites) {
				printWriter.println(s);
			}

			printWriter.close();
		} catch (Throwable e) {
			// ignored
		}
	}
}
