package com.github.mnesikos.samhain.common.entity;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SpiritEntity extends AnimalEntity {

    protected SpiritEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageableEntity) {
        return null;
    }
}
