package com.github.mnesikos.samhain.common.world.dimension;

import com.github.mnesikos.samhain.init.ModChunkGenerators;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;

import javax.annotation.Nullable;

public class OtherworldDimension extends Dimension {

    public OtherworldDimension(World p_i49936_1_, DimensionType p_i49936_2_) {
        super(p_i49936_1_, p_i49936_2_);
        this.setSkyRenderer(this::renderSky);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        return ModChunkGenerators.OTHERWORLD.create(world, BiomeProviderType.VANILLA_LAYERED.create(BiomeProviderType.VANILLA_LAYERED.createSettings().setGeneratorSettings(new OverworldGenSettings()).setWorldInfo(this.world.getWorldInfo())), ModChunkGenerators.OTHERWORLD.createSettings());
    }

    @Nullable
    public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
        for(int i = chunkPosIn.getXStart(); i <= chunkPosIn.getXEnd(); ++i) {
            for(int j = chunkPosIn.getZStart(); j <= chunkPosIn.getZEnd(); ++j) {
                BlockPos blockpos = this.findSpawn(i, j, checkValid);
                if (blockpos != null) {
                    return blockpos;
                }
            }
        }

        return null;
    }

    @Nullable
    public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(posX, 0, posZ);
        Biome biome = this.world.getBiome(blockpos$mutableblockpos);
        BlockState blockstate = biome.getSurfaceBuilderConfig().getTop();
        if (checkValid && !blockstate.getBlock().isIn(BlockTags.VALID_SPAWN)) {
            return null;
        } else {
            Chunk chunk = this.world.getChunk(posX >> 4, posZ >> 4);
            int i = chunk.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, posX & 15, posZ & 15);
            if (i < 0) {
                return null;
            } else if (chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE, posX & 15, posZ & 15) > chunk.getTopBlockY(Heightmap.Type.OCEAN_FLOOR, posX & 15, posZ & 15)) {
                return null;
            } else {
                for(int j = i + 1; j >= 0; --j) {
                    blockpos$mutableblockpos.setPos(posX, j, posZ);
                    BlockState blockstate1 = this.world.getBlockState(blockpos$mutableblockpos);
                    if (!blockstate1.getFluidState().isEmpty()) {
                        break;
                    }

                    if (blockstate1.equals(blockstate)) {
                        return blockpos$mutableblockpos.up().toImmutable();
                    }
                }

                return null;
            }
        }
    }

    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        double d0 = MathHelper.frac((double)worldTime / 24000.0D - 0.25D);
        double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
        return (float)(d0 * 2.0D + d1) / 3.0F;
    }

    public boolean isSurfaceWorld() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        float f = MathHelper.cos(celestialAngle * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        float r = 0.58823529F;
        float g = 0.55294117F;
        float b = 0.68627450F;
        //format = color / 255
        r = r * (f * 0.94F + 0.06F);
        g = g * (f * 0.94F + 0.06F);
        b = b * (f * 0.91F + 0.09F);
        return new Vec3d(r, g, b);
    }

    @Override
    public Vec3d getSkyColor(BlockPos cameraPos, float partialTicks) {
        float f = this.world.getCelestialAngle(partialTicks);
        float f1 = MathHelper.cos(f * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        int i = ForgeHooksClient.getSkyBlendColour(this.world, cameraPos);
        float r = (float)(i >> 16 & 97) / 255.0F;
        float g = (float)(i >> 8 & 83) / 255.0F;
        float b = (float)(i & 114) / 255.0F;
        //format = (i >> (n*8) & color) / 255.0F, color is the rgb color from 0 to 255, and for n r=2, g=1, b=0
        r = r * f1;
        g = g * f1;
        b = b * f1;
        float f6 = this.world.getRainStrength(partialTicks);
        if (f6 > 0.0F) {
            float f7 = (r * 0.3F + g * 0.59F + b * 0.11F) * 0.6F;
            float f8 = 1.0F - f6 * 0.75F;
            r = r * f8 + f7 * (1.0F - f8);
            g = g * f8 + f7 * (1.0F - f8);
            b = b * f8 + f7 * (1.0F - f8);
        }

        float f10 = this.world.getThunderStrength(partialTicks);
        if (f10 > 0.0F) {
            float f11 = (r * 0.3F + g * 0.59F + b * 0.11F) * 0.2F;
            float f9 = 1.0F - f10 * 0.75F;
            r = r * f9 + f11 * (1.0F - f9);
            g = g * f9 + f11 * (1.0F - f9);
            b = b * f9 + f11 * (1.0F - f9);
        }

        if (this.world.getLastLightningBolt() > 0) {
            float f12 = (float)this.world.getLastLightningBolt() - partialTicks;
            if (f12 > 1.0F) {
                f12 = 1.0F;
            }

            f12 = f12 * 0.45F;
            r = r * (1.0F - f12) + 0.8F * f12;
            g = g * (1.0F - f12) + 0.8F * f12;
            b = b * (1.0F - f12) + 1.0F * f12;
        }

        return new Vec3d(r, g, b);
    }

    @Override
    public Vec3d getCloudColor(float partialTicks) {
        float f = this.world.getCelestialAngle(partialTicks);
        float f1 = MathHelper.cos(f * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        float r = 0.40392156F;
        float g = 0.27058823F;
        float b = 0.43137254F;
        //format = color / 255
        float f5 = this.world.getRainStrength(partialTicks);
        if (f5 > 0.0F) {
            float f6 = (r * 0.3F + g * 0.59F + b * 0.11F) * 0.6F;
            float f7 = 1.0F - f5 * 0.95F;
            r = r * f7 + f6 * (1.0F - f7);
            g = g * f7 + f6 * (1.0F - f7);
            b = b * f7 + f6 * (1.0F - f7);
        }

        r = r * (f1 * 0.9F + 0.1F);
        g = g * (f1 * 0.9F + 0.1F);
        b = b * (f1 * 0.85F + 0.15F);
        float f9 = this.world.getThunderStrength(partialTicks);
        if (f9 > 0.0F) {
            float f10 = (r * 0.3F + g * 0.59F + b * 0.11F) * 0.2F;
            float f8 = 1.0F - f9 * 0.95F;
            r = r * f8 + f10 * (1.0F - f8);
            g = g * f8 + f10 * (1.0F - f8);
            b = b * f8 + f10 * (1.0F - f8);
        }

        return new Vec3d(r, g, b);
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean doesXZShowFog(int x, int z) {
        return true;
    }

    private void renderSky(int ticks, float partialTicks, ClientWorld world, Minecraft mc) {
        //todo
    }
}
