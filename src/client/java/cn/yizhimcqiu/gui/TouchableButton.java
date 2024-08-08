package cn.yizhimcqiu.gui;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TouchableButton extends ButtonWidget {
    protected Identifier texture;
    protected Text text;
    protected final boolean holdable;
    protected boolean holding;
    protected TouchableButton(int x, int y, Text message, Identifier texture, PressAction onPress, NarrationSupplier narrationSupplier, boolean holdable) {
        this(x, y, 20, 20, message, texture, onPress, narrationSupplier, holdable);
    }
    protected TouchableButton(int x, int y, int width, int height, Text message, Identifier texture, PressAction onPress, NarrationSupplier narrationSupplier, boolean holdable) {
        super(x, y, width, height, message, onPress, narrationSupplier);
        this.holdable = holdable;
        this.texture = texture;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.holding = true;
        if (!this.holdable) {
            this.onClick();
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.holding = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }
    public void tick() {
        if (this.holding && this.holdable) {
            this.onClick();
        }
    }
    public void onClick() {
        this.onPress.onPress(this);
    }

    @Override
    public void drawMessage(DrawContext context, TextRenderer textRenderer, int color) {
        int i = this.getX() + 2;
        int j = this.getX() + this.getWidth() - 2;
        if (this.texture != null) {
            context.drawGuiTexture(this.texture, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            drawScrollableText(context, textRenderer, this.getMessage(), i, this.getY(), j, this.getY() + this.getHeight(), color);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        protected int x, y;
        protected Text text;
        protected PressAction onPress;
        protected NarrationSupplier narrationSupplier;
        protected boolean holdable = false;
        protected Identifier texture;
        public Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }
        public Builder text(Text text) {
            this.text = text;
            return this;
        }
        public Builder onPress(PressAction onPress) {
            this.onPress = onPress;
            return this;
        }
        public Builder narrationSupplier(NarrationSupplier narrationSupplier) {
            this.narrationSupplier = narrationSupplier;
            return this;
        }
        public Builder holdable() {
            this.holdable = true;
            return this;
        }
        public Builder texture(Identifier texture) {
            this.texture = texture;
            return this;
        }
        public TouchableButton build() {
            return new TouchableButton(this.x, this.y, this.text == null ? Text.empty() : this.text, this.texture, this.onPress == null ? (button) -> {} : this.onPress, this.narrationSupplier == null ? (sup) -> Text.empty() : this.narrationSupplier, this.holdable);
        }
    }
}
