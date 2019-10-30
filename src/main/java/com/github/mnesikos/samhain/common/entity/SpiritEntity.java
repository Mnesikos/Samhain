package com.github.mnesikos.samhain.common.entity;

import com.github.mnesikos.samhain.Samhain;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpiritEntity extends AnimalEntity {

    private static final DataParameter<CompoundNBT> SPIRIT_TYPE = EntityDataManager.createKey(SpiritEntity.class, DataSerializers.COMPOUND_NBT);
    private static final Map<String, EntityType<?>> SERVER_TYPES = new HashMap<>();
    private static final Map<String, EntityType<?>> CLIENT_TYPES = new HashMap<>();
    private static Method clientRegisterGoals;
    @OnlyIn(Dist.CLIENT)
    private AgeableEntity base;

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
        this.dataManager.register(SPIRIT_TYPE, new CompoundNBT());
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
        EntityType<?> type;
        if(SERVER_TYPES.containsKey(name)) type = SERVER_TYPES.get(name);
        else {
            type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(name));
            SERVER_TYPES.put(name, type);
        }
        if(type != null) {
            Entity entity = type.create(world);
            if (entity instanceof AgeableEntity) {
                AgeableEntity ageable = (AgeableEntity) entity;
                ageable.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
                //useless assignment but done for the sake of syncing up both sides accurately, can be removed if needed
                base = ageable;
                //after this, the nbt data should not be changed, because the base entity will only be created once
                CompoundNBT nbt = getSpiritType().copy();
                nbt.putString("id", Objects.requireNonNull(ageable.getEntityString()));
                setSpiritType(ageable.writeWithoutTypeId(nbt));
            }
        }
        return p_213386_4_;
    }

    public void onKillCommand() {
        base = null;
        this.remove();
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
        if(this.ticksExisted >= 1200 && (this.ticksExisted == 6000 || (this.ticksExisted % 20 == 0 && this.rand.nextInt(100) == 0))) this.remove();
        super.livingTick();
    }


    @Override
    public boolean isInWater() {
        return false;
    }

    private CompoundNBT getSpiritType() {
        return this.dataManager.get(SPIRIT_TYPE);
    }

    private void setSpiritType(CompoundNBT p_213422_1_) {
        this.dataManager.set(SPIRIT_TYPE, p_213422_1_);
    }

    @OnlyIn(Dist.CLIENT)
    public AgeableEntity getBase() {
        if(base == null) {
            CompoundNBT compound = getSpiritType();
            if(!compound.isEmpty() && compound.contains("id")) {
                try {
                    EntityType.loadEntityUnchecked(compound, world).ifPresent(entity -> {
                        if (entity instanceof AgeableEntity) base = (AgeableEntity) entity;
                    });
                } catch (Exception e) {
                    String name = compound.getString("id");
                    EntityType<?> type;
                    if(CLIENT_TYPES.containsKey(name)) type = CLIENT_TYPES.get(name);
                    else {
                        type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(name));
                        CLIENT_TYPES.put(name, type);
                    }
                    if(type != null) {
                        Entity entity = type.create(world);
                        if (entity instanceof AgeableEntity) {
                            //spawning the entity from the client instead of the server in case the first attempt doesn't work(i'm looking at you, villagers)
                            AgeableEntity ageable = (AgeableEntity) entity;
                            CompoundNBT nbt = getSpiritType().copy();
                            nbt.putString("id", name);
                            setSpiritType(ageable.writeWithoutTypeId(nbt));
                            try {
                                if (clientRegisterGoals == null) {
                                    clientRegisterGoals = MobEntity.class.getDeclaredMethod("registerGoals");
                                    clientRegisterGoals.setAccessible(true);
                                }
                                clientRegisterGoals.invoke(ageable);
                                ageable.onInitialSpawn(world, world.getDifficultyForLocation(getPosition()), SpawnReason.COMMAND, null, nbt);
                            } catch (Exception ex) {
                                Samhain.LOGGER.fatal("Failed to create client side entity " + name + " for the Spirit", ex);
                            }
                            base = ageable;
                        }
                    }
                }
            }
        }
        return base;
    }

    public SpiritEntity createChild(AgeableEntity parent) {
        if (parent instanceof SpiritEntity) {
            SpiritEntity spirit = (SpiritEntity) parent;
            if(spirit.getSpiritType().getString("id").equals(getSpiritType().getString("id"))) {
                SpiritEntity child = ModEntities.SPIRIT.create(this.world);
                if (child != null) child.setSpiritType(this.rand.nextBoolean() ? getSpiritType().copy() : spirit.getSpiritType().copy());
                return child;
            }
        }
        return null;
    }

    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return this.isChild() ? p_213348_2_.height * 0.95F : 1.3F;
    }
}
