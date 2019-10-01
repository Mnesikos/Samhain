package com.github.mnesikos.samhain;

import com.github.mnesikos.samhain.init.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IProxy {
    ItemGroup itemGroup = new ItemGroup(Ref.MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.crimson_king_maple_log);
        }
    };

    void init();

    World getClientWorld();
}
