package dzwdz.toomanybinds.autocompletion;

import de.siphalor.amecs.api.PriorityKeyBinding;
import dzwdz.toomanybinds.mixinterface.KeyBindingMixinterface;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class BindSuggestion {
    public Text name;
    public Text category;
    public KeyBinding bind;
    private String searchable;
    public boolean favorite = false;

    public BindSuggestion(KeyBinding bind) {
        this.bind = bind;
        name = new TranslatableText(bind.getTranslationKey());
        category = new TranslatableText(bind.getCategory());
        searchable = (name.getString() + " " + category.getString()).toLowerCase();
    }

    public boolean matches(String[] searchTerms) {
        for (String term : searchTerms) {
            if (!searchable.contains(term)) return false;
        }
        return true;
    }

    public void execute() {
        MinecraftClient mc = MinecraftClient.getInstance();
        GameOptions options = mc.options;

        LauncherCompletion.addToHistory(getId());

        // workarounds for keybinds that are handled in dumb, incompatible ways
        if (bind == options.keyFullscreen) {
            mc.getWindow().toggleFullscreen();
            mc.options.fullscreen = mc.getWindow().isFullscreen();
            mc.options.write();
        } else if (bind == options.keyScreenshot) {
            ScreenshotRecorder.saveScreenshot(mc.runDirectory, mc.getFramebuffer(), (text) -> {
                mc.execute(() -> {
                    mc.inGameHud.getChatHud().addMessage(text);
                });
            });
        } else {
            ((KeyBindingMixinterface) bind).toomanybinds$press();
            // amecs compat
            bind.setPressed(true);
            bind.setPressed(false);
            if (bind instanceof PriorityKeyBinding) ((PriorityKeyBinding)bind).onPressedPriority();
        }
    }

    public String getId() {
        return bind.getTranslationKey();
    }
}
