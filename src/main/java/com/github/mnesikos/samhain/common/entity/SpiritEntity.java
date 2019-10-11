package com.github.mnesikos.samhain.common.entity;

import com.github.mnesikos.samhain.common.entity.goals.FollowEntityGoal;
import com.github.mnesikos.samhain.init.ModConfiguration;
import com.github.mnesikos.samhain.init.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpiritEntity extends AnimalEntity {

    private static final DataParameter<CompoundNBT> ENTITY_DATA = EntityDataManager.createKey(SpiritEntity.class, DataSerializers.COMPOUND_NBT);
    private static final Map<String, EntityType<?>> TYPES = new HashMap<>();
    public AgeableEntity base;

    public SpiritEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FollowEntityGoal(this, PlayerEntity.class, 30));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 6.0F));
    }
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20D);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(ENTITY_DATA, new CompoundNBT());
    }

    public void writeAdditional(CompoundNBT p_213281_1_) {
        super.writeAdditional(p_213281_1_);
        p_213281_1_.put("SpiritType", getSpiritType());
    }

    public void readAdditional(CompoundNBT p_70037_1_) {
        super.readAdditional(p_70037_1_);
        setSpiritType(p_70037_1_.getCompound("SpiritType"));
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
        p_213386_4_ = super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
        List<String> list = ModConfiguration.INSTANCE.spiritTypes.get();
        String name = list.get(rand.nextInt(list.size()));
        EntityType<?> type = TYPES.putIfAbsent(name, ForgeRegistries.ENTITIES.getValue(new ResourceLocation(name)));
        if(type != null) {
            Entity entity = type.create(world);
            if (entity instanceof AgeableEntity) {
                AgeableEntity ageable = (AgeableEntity) entity;
                ageable.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
                CompoundNBT nbt = getSpiritType().copy();
                nbt.putString("id", Objects.requireNonNull(ageable.getEntityString()));
                nbt = ageable.writeWithoutTypeId(nbt);
                base = ageable;
                setSpiritType(nbt);
            }
        }
        return p_213386_4_;
    }

    @Override
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
        return p_70097_1_ == DamageSource.OUT_OF_WORLD;
    }

    /*protected SoundEvent getAmbientSound() {
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return null;
    }

    protected SoundEvent getDeathSound() {
        return null;
    }

    protected float getSoundVolume() {
        return 1F;
    }*/

    @Override
    protected void collideWithEntity(Entity entityIn) {
        if(entityIn instanceof SpiritEntity){
            super.collideWithEntity(entityIn);
        }
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn) {
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public void livingTick() {
        if(this.ticksExisted >= 1200){
            if(this.ticksExisted == 6000){
                this.remove();
            }
            else{
                if(this.ticksExisted % 20 == 0){
                    if(this.rand.nextInt(100) + 1 == 1){
                        this.remove();
                    }
                }
            }
        }
        super.livingTick();
    }


    @Override
    public boolean isInWater() {
        return false;
    }

    private CompoundNBT getSpiritType() {
        return this.dataManager.get(ENTITY_DATA);
    }

    private void setSpiritType(CompoundNBT p_213422_1_) {
        this.dataManager.set(ENTITY_DATA, p_213422_1_);
        if(base == null) {
            EntityType.loadEntityUnchecked(p_213422_1_, world).ifPresent(entity -> {
                if(entity instanceof AgeableEntity) base = (AgeableEntity)entity;
            });
        }
    }

    public SpiritEntity createChild(AgeableEntity parent) {
        if (parent instanceof SpiritEntity) {
            SpiritEntity spirit = ((SpiritEntity) parent);
            if(spirit.getSpiritType().getString("id").equals(getSpiritType().getString("id"))) {
                SpiritEntity child = ModEntities.SPIRIT.create(this.world);
                if (child != null)
                    child.setSpiritType(this.rand.nextBoolean() ? getSpiritType().copy() : spirit.getSpiritType().copy());
                return child;
            }
        }
        return null;
    }

    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return this.isChild() ? p_213348_2_.height * 0.95F : 1.3F;
    }
}
