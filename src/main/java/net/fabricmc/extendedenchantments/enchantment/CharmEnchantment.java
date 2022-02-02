package net.fabricmc.extendedenchantments.enchantment;

import net.fabricmc.extendedenchantments.status_effects.CharmStatusEffect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;

public class CharmEnchantment extends Enchantment {
    public CharmEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public int getMinPower(int level) {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof MobEntity) {
            ((MobEntity) target)
                    .addStatusEffect(new StatusEffectInstance(new CharmStatusEffect(), 200, 1));
        }

        super.onTargetDamaged(user, target, level);
    }
}
