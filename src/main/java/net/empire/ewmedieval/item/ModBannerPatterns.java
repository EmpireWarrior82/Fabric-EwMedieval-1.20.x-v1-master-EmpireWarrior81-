package net.empire.ewmedieval.item;

import net.empire.ewmedieval.EwMedieval;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModBannerPatterns {

    public static final RegistryKey<BannerPattern> CUSTOM_PATTERN =
            RegistryKey.of(RegistryKeys.BANNER_PATTERN,
                    new Identifier(EwMedieval.MOD_ID, "custom_pattern"));

    public static void register() {
        EwMedieval.LOGGER.info("Registering Banner Patterns for " + EwMedieval.MOD_ID);
    }
}