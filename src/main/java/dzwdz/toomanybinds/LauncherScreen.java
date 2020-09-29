package dzwdz.toomanybinds;

import dzwdz.toomanybinds.mixinterface.KeyBindingMixinterface;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Map;

public class LauncherScreen extends Screen {
    protected final int SUGGESTION_COLOR = 0x999999;
    protected final int HIGHLIGHT_COLOR = 0xFFFF00;

    protected TextFieldWidget textField;
    protected LauncherCompletion completion;
    protected int selected = 0;
    public int w = 200;
    public int lineHeight = 12;

    protected LauncherScreen() {
        super(NarratorManager.EMPTY);
        completion = new LauncherCompletion();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        List<Map.Entry<String, KeyBinding>> suggestions = completion.getSuggestions();
        fill(matrices, (width-w)/2-1, (height-lineHeight)/2-2, (width+w)/2-1, (height+lineHeight)/2-1 + suggestions.size()*lineHeight, 0xAA000000);
        textField.setSelected(true);
        textField.render(matrices, mouseX, mouseY, delta);

        int y = (height-lineHeight)/2-1;
        int i = 0;
        for (Map.Entry<String, KeyBinding> suggestion : suggestions) {
            Text t = new LiteralText(suggestion.getKey());
            drawTextWithShadow(matrices, textRenderer, t, (width-w)/2, y += lineHeight, i++ == selected ? HIGHLIGHT_COLOR : SUGGESTION_COLOR);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_UP) selected--;
        else if (keyCode == GLFW.GLFW_KEY_DOWN) selected++;
        else if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            List<Map.Entry<String, KeyBinding>> suggestions = completion.getSuggestions();
            if (suggestions.size() > selected)
                ((KeyBindingMixinterface) suggestions.get(selected).getValue()).toomanybinds$press();
            client.openScreen(null);
            return true;
        }

        int totalCompletions = completion.getSuggestions().size();
        if (totalCompletions != 0)
            selected %= totalCompletions;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void textChangeListener(String s) {
        completion.updateSuggestions(s);
        selected = 0;
    }

    @Override
    protected void init() {
        client.keyboard.setRepeatEvents(true);
        textField = new TextFieldWidget(textRenderer, (width-w)/2, (height-lineHeight)/2, w, lineHeight, NarratorManager.EMPTY);
        textField.setHasBorder(false);
        textField.setChangedListener(this::textChangeListener);
        children.add(textField);
        setInitialFocus(textField);
    }

    @Override
    public void tick() {
        textField.tick();
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        String text = textField.getText();
        init(client, width, height);
        textField.setText(text);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void removed() {
        client.keyboard.setRepeatEvents(false);
    }
}
