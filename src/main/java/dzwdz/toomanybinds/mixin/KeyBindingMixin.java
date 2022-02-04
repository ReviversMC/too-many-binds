package dzwdz.toomanybinds.mixin;

import dzwdz.toomanybinds.mixinterface.KeyBindingMixinterface;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(KeyBinding.class)
public class KeyBindingMixin implements KeyBindingMixinterface {
    @Shadow private int timesPressed;

    @Override
    public void toomanybinds$press() {
        timesPressed++;
    }
}
