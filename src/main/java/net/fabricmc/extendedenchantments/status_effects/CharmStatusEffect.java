package net.fabricmc.extendedenchantments.status_effects;

import org.spongepowered.include.com.google.common.base.Predicate;

import net.fabricmc.extendedenchantments.mixin.MobAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class CharmStatusEffect extends StatusEffect {
    private Predicate<LivingEntity> TO_ATTACK = entity -> entity instanceof HostileEntity
            && !(entity instanceof PlayerEntity);
    // private static TargetPredicate TARGET_PREDICATE =
    // TargetPredicate.createAttackable().setBaseMaxDistance(16.0).setPredicate(TO_ATTACK);
    // private Set<PrioritizedGoal> goals = Sets.newLinkedHashSet();

    public CharmStatusEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xEC407A);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // @Override
    // public void onApplied(LivingEntity entity, AttributeContainer attributes, int
    // amplifier) {
    // if (entity instanceof MobEntity && goals.isEmpty()) {
    // goals.addAll(((MobAccessor) entity).getTargetSelector().getGoals());
    // ((MobAccessor) entity).getTargetSelector().clear();
    // ((MobAccessor) entity).getTargetSelector().add(1, new
    // ActiveTargetGoal<HostileEntity>(((MobEntity) entity), HostileEntity.class,
    // true));
    // }

    // super.onApplied(entity, attributes, amplifier);
    // }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof MobEntity) {
            World world = entity.getEntityWorld();

            // ActiveTargetGoal(((MobEntity) entity), HostileEntity.class, true);
            // ((MobAccessor) entity).getLivingTarget();
            LivingEntity closest = world.getClosestEntity(HostileEntity.class,
                    TargetPredicate.createAttackable().setBaseMaxDistance(16.0).setPredicate(TO_ATTACK), entity,
                    entity.getX(), entity.getY(), entity.getZ(),
                    entity.getBoundingBox().expand(16.0));
            ((MobAccessor) entity).setLivingTarget(closest);
            // ((MobEntity) entity).setTarget();
        }
    }

    // @Override
    // public void onRemoved(LivingEntity entity, AttributeContainer attributes, int
    // amplifier) {
    // if (entity instanceof MobEntity) {
    // ((MobAccessor) entity).getTargetSelector().clear();

    // for (PrioritizedGoal goal : goals) {
    // ((MobAccessor) entity).getTargetSelector().add(goal.getPriority(),
    // goal.getGoal());
    // }

    // goals.clear();
    // }

    // super.onRemoved(entity, attributes, amplifier);
    // }
}
