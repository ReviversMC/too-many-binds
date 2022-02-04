package dzwdz.toomanybinds;

import dzwdz.toomanybinds.autocompletion.BindSuggestion;
import dzwdz.toomanybinds.autocompletion.LauncherCompletion;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigManager;
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

    private int baseX;
    private int baseY;

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
        offsetX = TooManyBinds.config.launcherX;
        offsetY = TooManyBinds.config.launcherY;
    }


    // this is awful :)
    private double clampX(double x) {
        return Math.max(0, Math.min(x, width - w));
    }

    private double clampY(double y) {
        return Math.max(0, Math.min(y, height - lineHeight * (TooManyBinds.config.maxSuggestions + 1)));
    }

    private int getX() {
        return (int)clampX(baseX + offsetX);
    }

    private int getY() {
        return (int)clampY(baseY + offsetY);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        List<BindSuggestion> suggestions = completion.getSuggestions();
        int lineAmt = Math.min(suggestions.size(), TooManyBinds.config.maxSuggestions);
        int bgColor = (int)Math.round(TooManyBinds.config.bgOpacity * 255) * 0x1000000;
        fill(matrices, getX()-1, getY()-1, getX()+w-1, getY()+lineHeight-2 + lineAmt*lineHeight, bgColor);
        textField.setTextFieldFocused(true);
        textField.render(matrices, mouseX, mouseY, delta);

        int y = getY();
        for (int i = optionOffset; i - optionOffset < TooManyBinds.config.maxSuggestions; i++) {
            if (suggestions.size() <= i) break;
            BindSuggestion sg = suggestions.get(i);

            y += lineHeight;

            if (sg.favorite) {
                fill(matrices, getX()-3, y-2, getX()-1, y+lineHeight-2, HIGHLIGHT_COLOR | bgColor);
            }

            // draw the bind name
            drawTextWithShadow(matrices, textRenderer, sg.name, getX(), y, i == selected ? HIGHLIGHT_COLOR : SUGGESTION_COLOR);

            // draw the bind category
            int textWidth = textRenderer.getWidth(sg.category)+2;
            drawTextWithShadow(matrices, textRenderer, sg.category, getX()+w-textWidth, y, SUGGESTION_COLOR);
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
        if (TooManyBinds.favoriteKey.matchesKey(keyCode, scanCode)) {
            List<BindSuggestion> suggestions = completion.getSuggestions();
            if (suggestions.size() > selected) {
                LauncherCompletion.toggleFavorite(suggestions.get(selected));
                return true;
            }
        } else if (keyCode == GLFW.GLFW_KEY_UP) {
            switchSelection(-1);
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_DOWN) {
            switchSelection(1);
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            List<BindSuggestion> suggestions = completion.getSuggestions();
            client.setScreen(null);
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
        baseX = (width - w) / 2;
        baseY = (height - lineHeight) / 2;

        client.keyboard.setRepeatEvents(true);
        String text = "";
        if (textField != null) text = textField.getText();
        textField = new TextFieldWidget(textRenderer, getX(), getY()+1, w, lineHeight, NarratorManager.EMPTY);
        textField.setDrawsBackground(false);
        textField.setChangedListener(this::textChangeListener);
        textField.setText(text);
        addSelectableChild(textField);
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
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void removed() {
        client.keyboard.setRepeatEvents(false);
        TooManyBinds.config.launcherX = offsetX;
        TooManyBinds.config.launcherY = offsetY;
        ((ConfigManager<ModConfig>) AutoConfig.getConfigHolder(ModConfig.class)).save();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 1) {
            offsetX = 0;
            offsetY = 0;
            init();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        offsetX = clampX(offsetX + deltaX + baseX) - baseX;
        offsetY = clampY(offsetY + deltaY + baseY) - baseY;
        init();
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (Math.abs(offsetX) + Math.abs(offsetY) < 15) {
            offsetX = 0;
            offsetY = 0;
            init();
        }
        return false;
    }
}
