package com.github.reviversmc.toomanybinds.autocompletion;

import java.util.List;

public interface SuggestionProvider {
	void addEntries(List<BindSuggestion> binds);
}
