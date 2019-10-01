package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.Ref;
import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.common.item.SpiritEggItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Ref.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Ref.MOD_ID)
public class ModItems {
    @ObjectHolder(Ref.MOD_ID + ":spirit_egg")
    public static SpiritEggItem spirit_egg;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        Item.Properties properties = new Item.Properties().group(Samhain.proxy.itemGroup);
        event.getRegistry().registerAll(
                new SpiritEggItem()
        );
    }
}
