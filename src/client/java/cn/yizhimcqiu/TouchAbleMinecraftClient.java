package cn.yizhimcqiu;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;

public class TouchAbleMinecraftClient implements ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    @Override
    public void onInitializeClient() {
        LOGGER.info("Touchable Minecraft initialized.");
        LOGGER.warn("Warning: If you are using this mod in bulk (e.g. mobile launcher development), please pay 50 CNY to @YiZhiMCQiu !");

    }
}