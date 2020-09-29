package dzwdz.toomanybinds;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;

public class LauncherScreen extends Screen {
    protected TextFieldWidget textField;
    public int w = 200;
    public int h = 10;

    protected LauncherScreen() {
        super(NarratorManager.EMPTY);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        fill(matrices, (width-w)/2-1, (height-h)/2-1, (width+w)/2-1, (height+h)/2-1, 0xAA000000);
        textField.setSelected(true);
        textField.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void init() {
        client.keyboard.setRepeatEvents(true);
        textField = new TextFieldWidget(textRenderer, (width-w)/2, (height-h)/2, w, h, NarratorManager.EMPTY);
        textField.setHasBorder(false);
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
