package dzwdz.toomanybinds;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class LauncherScreen extends Screen {
    protected final int SUGGESTION_COLOR = 0x999999;
    protected final int HIGHLIGHT_COLOR = 0xFFFF00;

    protected TextFieldWidget textField;
    protected LauncherCompletion completion;
    protected int selected = 0;
    public int w = 250;
    public int lineHeight = 12;

    protected LauncherScreen() {
        super(NarratorManager.EMPTY);
        completion = new LauncherCompletion();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        List<BindSuggestion> suggestions = completion.getSuggestions();
        fill(matrices, (width-w)/2-1, (height-lineHeight)/2-2, (width+w)/2-1, (height+lineHeight)/2-1 + suggestions.size()*lineHeight, 0xAA000000);
        textField.setSelected(true);
        textField.render(matrices, mouseX, mouseY, delta);

        int y = (height-lineHeight)/2-1;
        int i = 0;
        for (BindSuggestion sg : suggestions) {
            // todo put this in some other function
            drawTextWithShadow(matrices, textRenderer, sg.name, (width-w)/2, y += lineHeight, i++ == selected ? HIGHLIGHT_COLOR : SUGGESTION_COLOR);
            int offset = textRenderer.getWidth(sg.category)+2;
            drawTextWithShadow(matrices, textRenderer, sg.category, (width+w)/2-offset, y, SUGGESTION_COLOR);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    public void switchSelection(int by) {
        selected += by;
        int totalCompletions = completion.getSuggestions().size();
        if (totalCompletions != 0)
            selected = (selected + totalCompletions) % totalCompletions;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_UP) switchSelection(-1);
        else if (keyCode == GLFW.GLFW_KEY_DOWN) switchSelection(1);
        else if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            List<BindSuggestion> suggestions = completion.getSuggestions();
            if (suggestions.size() > selected)
                suggestions.get(selected).execute();
            client.openScreen(null);
            return true;
        }

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
