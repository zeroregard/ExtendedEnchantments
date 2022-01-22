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
public class AcrobaticsMixin {
    private int extraJumps = 0;
    private boolean jumpedLastTick = false;
    private int acrobaticsLevel = 0;

    // TODO: ignore fall damage from 4 and below?
    // TODO: implement jumping off of water at level 2
    // TODO: implement bunny-hopping at level 3

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo info) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        acrobaticsLevel = EnchantmentHelper.getEquipmentLevel(ExtendedEnchantments.ACROBATICS, player);
        if (shouldResetJumps(player)) {
            // reset the allowed extra jumps
            extraJumps = acrobaticsLevel >= 1 ? 1 : 0;
        } else if (eligibleForJumping(player)) { 
            if (playerAttemptingToJump(player)) {
                handleJump(player);
            }
        }
        jumpedLastTick = player.input.jumping;
    }

    private boolean shouldResetJumps(ClientPlayerEntity player) {
        return player.isOnGround() || player.isClimbing();
    }

    private boolean eligibleForJumping(ClientPlayerEntity player) {
        // not really sure what this velocity check is doing per se, but doesn't work without it
        return !jumpedLastTick && extraJumps > 0 &&(player.getVelocity().y < 0); 
    }

    private boolean playerAttemptingToJump(ClientPlayerEntity player) {
        return player.input.jumping && !player.getAbilities().flying;
    }

    private void handleJump(ClientPlayerEntity player) {
        if (canJump(player)) {
            --extraJumps;
            player.jump();
        }
    }

    private boolean wearingUsableElytra(ClientPlayerEntity player) {
        ItemStack chestItemStack = player.getEquippedStack(EquipmentSlot.CHEST);
        return chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isUsable(chestItemStack);
    }

    private boolean canJump(ClientPlayerEntity player) {
        return !wearingUsableElytra(player) && !player.isFallFlying() && !player.hasVehicle()
                && !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION);
    }
}