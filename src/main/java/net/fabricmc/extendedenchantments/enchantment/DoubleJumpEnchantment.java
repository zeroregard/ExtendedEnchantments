package net.fabricmc.extendedenchantments.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class DoubleJumpEnchantment extends Enchantment {
    public DoubleJumpEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[] { EquipmentSlot.FEET });
    }

    @Override
    public int getMinPower(int level) {
        return 5 + 5 * level;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
