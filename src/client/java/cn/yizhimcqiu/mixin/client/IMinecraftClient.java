package cn.yizhimcqiu.mixin.client;

import cn.yizhimcqiu.TouchAbleMinecraftClient;
import cn.yizhimcqiu.gui.TouchableButton;
import cn.yizhimcqiu.util.ObfRefHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Mixin(MinecraftClient.class)
public class IMinecraftClient {
    @Inject(at = @At("TAIL"), method = "tick")
    private void init(CallbackInfo info) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.currentScreen != null) {
            try {
                List<Drawable> drawables = (List<Drawable>) ObfRefHelper.findField(Screen.class, "drawables").get(mc.currentScreen);;
                for (Drawable drawable : drawables) {
                    if (drawable instanceof TouchableButton button) {
                        button.tick();
                    }
                }
            } catch (Exception e) {
                TouchAbleMinecraftClient.LOGGER.error(e.getClass().getName()+": ", e);
            }
        }
    }
    @Inject(method = "setScreen", at = @At("TAIL"))
    private void setScreen(@Nullable Screen screen, CallbackInfo info) {
        if (screen != null) {
            for (Method m : Screen.class.getMethods()) {
                if (m.getName().equals("addDrawableChild")) {
                    try {
                        m.invoke(screen, TouchableButton.builder()
                                .holdable()
                                .position(10, 10)
                                .onPress(button -> {
                                    TouchAbleMinecraftClient.LOGGER.error("PRESSED!!!!!!!");
                                })
                                .build());
                    } catch (Exception ignored) { }
                }
            }
        }
    }
}