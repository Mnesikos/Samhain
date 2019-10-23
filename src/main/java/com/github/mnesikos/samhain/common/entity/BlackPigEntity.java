package com.github.mnesikos.samhain.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class BlackPigEntity extends TameableEntity {
    private static final DataParameter<Boolean> HAS_LADY_GWEN = EntityDataManager.createKey(BlackPigEntity.class, DataSerializers.BOOLEAN);

    public BlackPigEntity(EntityType<? extends TameableEntity> entity, World world) {
        super(entity, world);
    }

    @Override
    protected void registerGoals() {
        this.sitGoal = new SitGoal(this);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, this.sitGoal);
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, true));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HAS_LADY_GWEN, false);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("HasLadyGwen", this.hasLadyGwen());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        String s;
        if (compound.contains("OwnerUUID")) {
            s = compound.getString("OwnerUUID");
            if (!s.isEmpty())
                this.setOwnerId(UUID.fromString(s));
        }
        this.setHasLadyGwen(compound.getBoolean("HasLadyGwen"));
    }

    public boolean hasLadyGwen() {
        return this.dataManager.get(HAS_LADY_GWEN);
    }

    public void setHasLadyGwen(boolean hasLadyGwen) {
        this.dataManager.set(HAS_LADY_GWEN, hasLadyGwen);
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        if (this.isTamed() && this.hasLadyGwen()) {
            LadyGwenEntity gwen = null;
            UUID gwenId = this.getOwnerId();
            if (gwenId != null) {
                int f = 20;
                BlockPos posMin = this.getPosition().add(-f, -3, -f);
                BlockPos posMax = this.getPosition().add(f, 6, f);
                AxisAlignedBB boundingBox = new AxisAlignedBB(posMin, posMax);
                List<LadyGwenEntity> list = this.world.getEntitiesWithinAABB(LadyGwenEntity.class, boundingBox);

                for (LadyGwenEntity ladyGwenEntity : list) {
                    if (ladyGwenEntity != null && ladyGwenEntity.getUniqueID().equals(gwenId)) {
                        gwen = ladyGwenEntity;
                        break;
                    }
                }
            }
            return gwen;
        }
        return super.getOwner();
    }

    @Override
    public void livingTick() {
        if (this.isTamed() && this.hasLadyGwen()) {
            if ((this.getOwner() != null && !this.getOwner().isAlive()) || this.getOwner() == null) {
                this.setTamed(false);
                this.setOwnerId(null);
                this.setHasLadyGwen(false);
            }
        }
        super.livingTick();
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (this.isTamed() && this.getOwner() == player) {
            if (!itemstack.isEmpty()) {
                if (item.isFood()) {
                    if (this.getHealth() < this.getMaxHealth()) {
                        if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }

                        this.heal((float)item.getFood().getHealing());
                        return true;
                    }
                }
            }

            if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(itemstack)) {
                this.sitGoal.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget(null);
            }
        } else if (!this.isTamed() && item.isFood() && this.getAttackTarget() != player) {
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }

            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget(null);
                    this.sitGoal.setSitting(true);
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte)7);
                } else {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte)6);
                }
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
            if (target instanceof BlackPigEntity) {
                BlackPigEntity pigEntity = (BlackPigEntity)target;
                if (pigEntity.isTamed() && pigEntity.getOwner() == owner)
                    return false;
            }

            if (target instanceof LadyGwenEntity || target instanceof SpiritEntity)
                return false;
            else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).canAttackPlayer((PlayerEntity)target))
                return false;
            else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity)target).isTame())
                return false;
            else
                return !(target instanceof CatEntity) || !((CatEntity)target).isTamed();

        } else
            return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source))
            return false;

        else {
            Entity entity = source.getTrueSource();
            if (this.sitGoal != null)
                this.sitGoal.setSitting(false);

            if (entity != null && !(entity instanceof AbstractArrowEntity))
                amount = (amount + 1.0F) / 2.0F;

            // todo give the pig some passive healing ability cause otherwise it gon die all the time
            if (this.getHealth() < this.getMaxHealth() / 2)
                this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, 0, false, false));

            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public boolean canMateWith(AnimalEntity otherAnimal) {
        return false;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageableEntity) {
        return null;
    }

    @Override
    protected boolean canBeRidden(Entity p_184228_1_) {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }
}
