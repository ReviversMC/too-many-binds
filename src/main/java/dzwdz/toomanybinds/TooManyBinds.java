package dzwdz.toomanybinds;

import dzwdz.toomanybinds.autocompletion.LauncherCompletion;
import dzwdz.toomanybinds.autocompletion.VanillaKeybindSuggestions;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class TooManyBinds implements ModInitializer {
    public static ModConfig config;

    public static KeyBinding launcherKey;
    public static KeyBinding favoriteKey;

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        launcherKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.toomanybinds.launcher",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.toomanybinds"
        ));
        favoriteKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.toomanybinds.favorite",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F4,
                "category.toomanybinds"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (launcherKey.wasPressed()) client.setScreen(new LauncherScreen());
        });

        ClientLifecycleEvents.CLIENT_STARTED.register(t -> LauncherCompletion.loadData());
        ClientLifecycleEvents.CLIENT_STOPPING.register(t -> LauncherCompletion.saveData());

        LauncherCompletion.suggestionProviders.add(new VanillaKeybindSuggestions());
    }
}
