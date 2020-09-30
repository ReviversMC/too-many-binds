package dzwdz.toomanybinds;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class TooManyBinds implements ModInitializer {
    public static ModConfig config;

    private static KeyBinding launcherKey;

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

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (launcherKey.wasPressed()) client.openScreen(new LauncherScreen());
        });

        ClientLifecycleEvents.CLIENT_STARTED.register(t -> LauncherCompletion.loadHistory());
        ClientLifecycleEvents.CLIENT_STOPPING.register(t -> LauncherCompletion.saveHistory());
    }
}
