package com.github.mnesikos.samhain.common.entity;

import net.minecraft.block.SoundType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class BlackHorseEntity extends AbstractHorseEntity {
    private static final DataParameter<Boolean> HAS_DULLAHAN = EntityDataManager.createKey(BlackHorseEntity.class, DataSerializers.BOOLEAN);

    public BlackHorseEntity(EntityType<? extends AbstractHorseEntity> entity, World worldIn) {
        super(entity, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.initExtraAI();
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.getModifiedMaxHealth());
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.getModifiedMovementSpeed());
        this.getAttribute(JUMP_STRENGTH).setBaseValue(this.getModifiedJumpStrength());
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HAS_DULLAHAN, false);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("HasDullahan", this.hasDullahan());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        String s;
        if (compound.contains("OwnerUUID")) {
            s = compound.getString("OwnerUUID");
            if (!s.isEmpty())
                this.setOwnerUniqueId(UUID.fromString(s));
        }
        this.setHasDullahan(compound.getBoolean("HasDullahan"));
    }

    public boolean hasDullahan() {
        return this.dataManager.get(HAS_DULLAHAN);
    }

    public void setHasDullahan(boolean hasDullahan) {
        this.dataManager.set(HAS_DULLAHAN, hasDullahan);
    }

    private DullahanEntity getDullahan() {
        if (this.isTame() && this.hasDullahan()) {
            DullahanEntity dullahan = null;
            UUID dullahanId = this.getOwnerUniqueId();
            if (dullahanId != null) {
                int f = 20;
                BlockPos posMin = this.getPosition().add(-f, -3, -f);
                BlockPos posMax = this.getPosition().add(f, 6, f);
                AxisAlignedBB boundingBox = new AxisAlignedBB(posMin, posMax);
                List<DullahanEntity> list = this.world.getEntitiesWithinAABB(DullahanEntity.class, boundingBox);

                for (DullahanEntity DullahanEntity : list) {
                    if (DullahanEntity != null && DullahanEntity.getUniqueID().equals(dullahanId)) {
                        dullahan = DullahanEntity;
                        break;
                    }
                }
            }
            return dullahan;
        }
        return null;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.setGrowingAge(0);
        return spawnDataIn;
    }

    @Override
    public void livingTick() {
        /*if (this.world.isRemote) {
            for(int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.FLAME,
                        this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.getWidth(),
                        this.posY + this.rand.nextDouble() * (double)this.getHeight() - 0.25D,
                        this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.getWidth(),
                        (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(),
                        (this.rand.nextDouble() - 0.5D) * 2.0D); // todo get this particle shit together
            }
        }*/

        if (this.isTame() && this.hasDullahan()) {
            /*if (this.getOwnerUniqueId() != null && this.getDullahan() != null && !this.getDullahan().isAlive()) {
                if (this.getDullahan().getLastDamageSource() != null && this.getDullahan().getLastDamageSource().getImmediateSource() instanceof AbstractArrowEntity) {
                    this.setHorseTamed(false);
                    this.setOwnerUniqueId(null);
                    this.setHasDullahan(false);
                } else {
                    this.remove();
                }

            } else if (this.getDullahan() == null) {
                this.remove();

            } else */if (this.getDullahan() != null && this.getPassengers().isEmpty()) {
                DullahanEntity dullahan = this.getDullahan();
                dullahan.startRiding(this);
            }
        }

        super.livingTick();
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = !itemstack.isEmpty();
        if (flag && itemstack.getItem() instanceof SpawnEggItem) {
            return false;
        } else {
            if (!this.isChild()) {
                if (this.isTame() && player.isSneaking()) {
                    this.openGUI(player);
                    return true;
                }

                if (this.isBeingRidden()) {
                    return super.processInteract(player, hand);
                }
            }

            if (flag) {
                if (this.handleEating(player, itemstack)) {
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    return true;
                }

                if (itemstack.interactWithEntity(player, this, hand)) {
                    return true;
                }

                if (!this.isTame()) {
                    this.makeMad();
                    return true;
                }

                boolean flag1 = !this.isChild() && !this.isHorseSaddled() && itemstack.getItem() == Items.SADDLE;
                if (this.isArmor(itemstack) || flag1) {
                    this.openGUI(player);
                    return true;
                }
            }

            if (this.isChild()) {
                return super.processInteract(player, hand);
            } else {
                this.mountTo(player);
                return true;
            }
        }
    }

    @Override
    protected boolean canMate() {
        return false;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    protected void playGallopSound(SoundType p_190680_1_) {
        super.playGallopSound(p_190680_1_);
        if (this.rand.nextInt(10) == 0) {
            this.playSound(SoundEvents.ENTITY_HORSE_BREATHE, p_190680_1_.getVolume() * 0.6F, p_190680_1_.getPitch());
        }
    }

    protected SoundEvent getAmbientSound() {
        super.getAmbientSound();
        return SoundEvents.ENTITY_HORSE_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        super.getDeathSound();
        return SoundEvents.ENTITY_HORSE_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        super.getHurtSound(damageSourceIn);
        return SoundEvents.ENTITY_HORSE_HURT;
    }

    protected SoundEvent getAngrySound() {
        super.getAngrySound();
        return SoundEvents.ENTITY_HORSE_ANGRY;
    }
}
