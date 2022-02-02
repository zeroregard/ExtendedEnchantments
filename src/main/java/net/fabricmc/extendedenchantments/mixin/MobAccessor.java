package net.fabricmc.extendedenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;

@Mixin(MobEntity.class)
public interface MobAccessor {

    @Accessor("goalSelector")
    GoalSelector getGoalSelector();

    @Accessor("targetSelector")
    GoalSelector getTargetSelector();

    @Accessor("target")
    LivingEntity getLivingTarget();

    @Accessor("target")
    public void setLivingTarget(LivingEntity entity);
}
