package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.Samhain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

public class ModItems extends ModRegistry<Item> {
    //moved group here since i removed the proxies
    public static final ItemGroup GROUP = new ItemGroup(Samhain.MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.CRIMSON_KING_MAPLE_LOG);
        }
    };
}
