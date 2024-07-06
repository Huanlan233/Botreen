package top.htext.botreen;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class BotreenReference {
    public static final String MOD_ID = "botreen";
    public static Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static String MOD_VERSION;

    static {
        Optional<ModContainer> optional = FabricLoader.getInstance().getModContainer(MOD_ID);
        optional.ifPresent(modContainer -> MOD_VERSION = modContainer.getMetadata().getVersion().getFriendlyString());
    }

    public static Identifier identifier(String s) {
        return new Identifier(s);
    }
}
