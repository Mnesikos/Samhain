package com.github.mnesikos.samhain.common.block;

import com.github.mnesikos.samhain.init.ModDimensions;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class OtherworldPortalBlock extends ModBlock {
    public OtherworldPortalBlock() {
        super("otherworld_portal", Block.Properties.create(Material.PORTAL).doesNotBlockMovement().tickRandomly().hardnessAndResistance(-1.0F).sound(SoundType.GLASS).lightValue(11).noDrops(), false);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public void onEntityCollision(BlockState p_196262_1_, World p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_) {
        super.onEntityCollision(p_196262_1_, p_196262_2_, p_196262_3_, p_196262_4_);
        if(p_196262_4_ instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) p_196262_4_;
            DimensionType destination = p_196262_2_.dimension.getType() == ModDimensions.TYPES.get(ModDimensions.OTHERWORLD) ? DimensionType.OVERWORLD : ModDimensions.TYPES.get(ModDimensions.OTHERWORLD);
            player.invulnerableDimensionChange = true;
            DimensionType dimensiontype = player.dimension;
            ServerWorld serverworld = player.server.getWorld(dimensiontype);
            player.dimension = destination;
            ServerWorld serverworld1 = player.server.getWorld(destination);
            WorldInfo worldinfo = player.world.getWorldInfo();
            player.connection.sendPacket(new SRespawnPacket(destination, worldinfo.getGenerator(), player.interactionManager.getGameType()));
            player.connection.sendPacket(new SServerDifficultyPacket(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
            PlayerList playerlist = player.server.getPlayerList();
            playerlist.updatePermissionLevel(player);
            serverworld.removeEntity(player, true);
            player.revive();
            double d0 = player.posX;
            double d1 = player.posY;
            double d2 = player.posZ;
            float f = player.rotationPitch;
            float f1 = player.rotationYaw;
            serverworld.getProfiler().startSection("moving");
            double moveFactor = serverworld.getDimension().getMovementFactor() / serverworld1.getDimension().getMovementFactor();
            d0 *= moveFactor;
            d2 *= moveFactor;
            player.setLocationAndAngles(d0, d1, d2, f1, f);
            serverworld.getProfiler().endSection();
            serverworld.getProfiler().startSection("placing");
            double d7 = Math.min(-2.9999872E7D, serverworld1.getWorldBorder().minX() + 16.0D);
            double d4 = Math.min(-2.9999872E7D, serverworld1.getWorldBorder().minZ() + 16.0D);
            double d5 = Math.min(2.9999872E7D, serverworld1.getWorldBorder().maxX() - 16.0D);
            double d6 = Math.min(2.9999872E7D, serverworld1.getWorldBorder().maxZ() - 16.0D);
            d0 = MathHelper.clamp(d0, d7, d5);
            d2 = MathHelper.clamp(d2, d4, d6);
            player.setLocationAndAngles(d0, d1, d2, f1, f);
            serverworld.getProfiler().endSection();
            player.setWorld(serverworld1);
            serverworld1.func_217447_b(player);
            DimensionType oldtype = serverworld.dimension.getType();
            DimensionType dimensiontype1 = player.world.dimension.getType();
            CriteriaTriggers.CHANGED_DIMENSION.trigger(player, oldtype, dimensiontype1);
            player.connection.setPlayerLocation(player.posX, player.posY, player.posZ, f1, f);
            player.interactionManager.setWorld(serverworld1);
            player.connection.sendPacket(new SPlayerAbilitiesPacket(player.abilities));
            playerlist.sendWorldInfo(player, serverworld1);
            playerlist.sendInventory(player);
            for (EffectInstance effectinstance : player.getActivePotionEffects()) player.connection.sendPacket(new SPlayEntityEffectPacket(player.getEntityId(), effectinstance));
            player.connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
            player.lastExperience = -1;
            player.lastHealth = -1.0F;
            player.lastFoodLevel = -1;
        }
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return ItemStack.EMPTY;
    }
}
