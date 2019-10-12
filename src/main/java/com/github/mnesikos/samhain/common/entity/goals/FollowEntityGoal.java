package com.github.mnesikos.samhain.common.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;

import java.util.EnumSet;
import java.util.List;

public class FollowEntityGoal extends Goal {
    private final AnimalEntity entity;
    private final Class<? extends LivingEntity> followedEntityClass;
    private LivingEntity followingEntity;
    private final PathNavigator navigation;
    private int timeToRecalcPath;
    private float oldWaterCost;
    private final float areaSize;

    public FollowEntityGoal(AnimalEntity entityIn, Class<? extends LivingEntity> followedEntClass, float areaIn) {
        this.entity = entityIn;
        this.followedEntityClass = followedEntClass;
        this.navigation = entityIn.getNavigator();
        this.areaSize = areaIn;
        this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean shouldExecute() {
        List<LivingEntity> lvt_1_1_ = this.entity.world.getEntitiesWithinAABB(this.followedEntityClass, this.entity.getBoundingBox().grow((double)this.areaSize), null);
        for(LivingEntity ent:lvt_1_1_) {
            if (!ent.isInvisible()) {
                this.followingEntity = ent;
                return true;
            }
        }
        return false;
    }

    public boolean shouldContinueExecuting() {
        return this.followingEntity != null && !this.navigation.noPath();
    }

    public void startExecuting() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.entity.getPathPriority(PathNodeType.WATER);
        this.entity.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    public void resetTask() {
        this.followingEntity = null;
        this.navigation.clearPath();
        this.entity.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }

    public void tick() {
        if (this.followingEntity != null && !this.entity.getLeashed()) {
            this.entity.getLookController().setLookPositionWithEntity(this.followingEntity, 10.0F, (float)this.entity.getVerticalFaceSpeed());
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;
                this.navigation.tryMoveToEntityLiving(this.followingEntity, 1.2);
            }
        }
    }
}

