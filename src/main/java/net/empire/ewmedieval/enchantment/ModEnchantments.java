package net.empire.ewmedieval.enchantment;

import net.empire.ewmedieval.EwMedieval;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {

    public static final Enchantment BACKSTABBING = Registry.register(
            Registries.ENCHANTMENT,
            new Identifier(EwMedieval.MOD_ID, "backstabbing"),
            new BackstabbingEnchantment(
                    Enchantment.Rarity.RARE,
                    EnchantmentTarget.WEAPON,
                    new EquipmentSlot[]{ EquipmentSlot.MAINHAND }
            ));

    public static void registerEnchantments() {
    }
}