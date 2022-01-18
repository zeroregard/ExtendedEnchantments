package net.fabricmc.extendedenchantments.mixin;

import net.fabricmc.extendedenchantments.ExtendedEnchantments;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// based on https://github.com/TurtleArmyMc/DoubleJump/blob/master/src/main/java/com/turtlearmymc/doublejump/mixin/DoubleJumpMixin.java

@Mixin(ClientPlayerEntity.class)
public class DoubleJumpMixin {
    private int extraJumps = 0;
    private boolean jumpedLastTick = false;
    private boolean superiorVersion = false;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo info) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        int doubleJumpLevel = EnchantmentHelper.getEquipmentLevel(ExtendedEnchantments.DOUBLE_JUMP, player);
        superiorVersion = doubleJumpLevel == 2;
        if (player.isOnGround() || player.isClimbing()) {
            // reset the allowed extra jumps
            extraJumps = doubleJumpLevel >= 1 ? 1 : 0;
        } else if (!jumpedLastTick && extraJumps > 0 && (player.getVelocity().y < 0)) {
            // player's off the ground and is attempting to jump:
            if (player.input.jumping && !player.getAbilities().flying) {
                if (canJump(player)) {
                    --extraJumps;
                    player.jump();
                }
            }
        }
        jumpedLastTick = player.input.jumping;
    }

    private boolean wearingUsableElytra(ClientPlayerEntity player) {
        ItemStack chestItemStack = player.getEquippedStack(EquipmentSlot.CHEST);
        return chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isUsable(chestItemStack);
    }

    private boolean canJump(ClientPlayerEntity player) {
        boolean isTouchingWater = player.isTouchingWater() && !this.superiorVersion;
        if(player.isTouchingWater()) {
            extraJumps++;
        }
        return !wearingUsableElytra(player) && !player.isFallFlying() && !player.hasVehicle()
                && !isTouchingWater && !player.hasStatusEffect(StatusEffects.LEVITATION);
    }
}