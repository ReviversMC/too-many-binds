package dzwdz.toomanybinds;

import dzwdz.toomanybinds.autocompletion.BindSuggestion;
import dzwdz.toomanybinds.autocompletion.LauncherCompletion;
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

    public static double offsetX = 0;
    public static double offsetY = 0;

    protected TextFieldWidget textField;
    protected LauncherCompletion completion;
    protected int selected = 0;
    protected int optionOffset = 0;
    public int w = 250;
    public int lineHeight = 12;

    protected LauncherScreen() {
        super(NarratorManager.EMPTY);
        completion = new LauncherCompletion();
        completion.updateSuggestions("");
    }

    private int getX() {
        return width / 2 + (int)offsetX;
    }

    private int getY() {
        return height / 2 + (int)offsetY;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        List<BindSuggestion> suggestions = completion.getSuggestions();
        int lineAmt = Math.min(suggestions.size(), TooManyBinds.config.maxSuggestions);
        fill(matrices, getX()-w/2-1, getY()-lineHeight/2-2, getX()+w/2-1, getY()+lineHeight/2-3 + lineAmt*lineHeight, 0xAA000000);
        textField.setSelected(true);
        textField.render(matrices, mouseX, mouseY, delta);

        int y = getY()-lineHeight/2-1;
        for (int i = optionOffset; i - optionOffset < TooManyBinds.config.maxSuggestions; i++) {
            if (suggestions.size() <= i) break;
            BindSuggestion sg = suggestions.get(i);

            // draw the bind name
            drawTextWithShadow(matrices, textRenderer, sg.name, getX()-w/2, y += lineHeight, i == selected ? HIGHLIGHT_COLOR : SUGGESTION_COLOR);

            // draw the bind category
            int offset = textRenderer.getWidth(sg.category)+2;
            drawTextWithShadow(matrices, textRenderer, sg.category, getX()+w/2-offset, y, SUGGESTION_COLOR);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    public void switchSelection(int by) {
        int totalCompletions = completion.getSuggestions().size();
        if (totalCompletions != 0) {
            selected = (selected + by + totalCompletions) % totalCompletions;
            if (optionOffset > selected)
                optionOffset = selected;
            else if (optionOffset + TooManyBinds.config.maxSuggestions <= selected)
                optionOffset = selected - TooManyBinds.config.maxSuggestions + 1;
        } else {
            selected = 0;
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_UP) switchSelection(-1);
        else if (keyCode == GLFW.GLFW_KEY_DOWN) switchSelection(1);
        else if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            List<BindSuggestion> suggestions = completion.getSuggestions();
            client.openScreen(null);
            if (suggestions.size() > selected)
                suggestions.get(selected).execute();
            return true;
        }

        return textField.keyPressed(keyCode, scanCode, modifiers)
                || super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void textChangeListener(String s) {
        completion.updateSuggestions(s);
        selected = 0;
        optionOffset = 0;
    }

    @Override
    protected void init() {
        client.keyboard.setRepeatEvents(true);
        String text = "";
        if (textField != null) text = textField.getText();
        textField = new TextFieldWidget(textRenderer, getX()-w/2, getY()-lineHeight/2, w, lineHeight, NarratorManager.EMPTY);
        textField.setHasBorder(false);
        textField.setChangedListener(this::textChangeListener);
        textField.setText(text);
        children.add(textField);
        setInitialFocus(textField);
    }

    @Override
    public void tick() {
        textField.tick();
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        init(client, width, height);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void removed() {
        client.keyboard.setRepeatEvents(false);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        offsetX += deltaX;
        offsetY += deltaY;
        init();
        return true;
    }
}
