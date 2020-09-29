package dzwdz.toomanybinds;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class TooManyBinds implements ModInitializer {
    private static KeyBinding launcherKey;
    @Override
    public void onInitialize() {
        launcherKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.toomanybinds.launcher",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                "category.toomanybinds"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (launcherKey.wasPressed()) client.openScreen(new LauncherScreen());

        });
    }
}
