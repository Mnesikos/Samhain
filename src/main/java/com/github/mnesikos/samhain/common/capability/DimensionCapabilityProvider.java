package com.github.mnesikos.samhain.common.capability;

import com.github.mnesikos.samhain.Samhain;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DimensionCapabilityProvider extends DimensionItemHolder implements ICapabilityProvider {
    @CapabilityInject(DimensionItemHolder.class)
    public static Capability<DimensionItemHolder> CAPABILITY = null;
    public static final ResourceLocation HOLDER_RESOURCE = new ResourceLocation(Samhain.MOD_ID, "item_holder");
    private final LazyOptional<DimensionItemHolder> holder = LazyOptional.of(() -> this);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return CAPABILITY.orEmpty(capability, holder);
    }

    public static class HolderStorage implements Capability.IStorage<DimensionItemHolder> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<DimensionItemHolder> capability, DimensionItemHolder instance, Direction side) {
            return instance.inventory;
        }

        @Override
        public void readNBT(Capability<DimensionItemHolder> capability, DimensionItemHolder instance, Direction side, INBT nbt) {
            instance.inventory = (ListNBT)nbt;
        }
    }
}
