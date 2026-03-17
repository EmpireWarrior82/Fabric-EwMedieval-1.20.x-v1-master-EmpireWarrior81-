package net.empire.ewmedieval.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public interface CustomEnchantingBehaviorItem {
    boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment);
}