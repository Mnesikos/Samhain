package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.common.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModEntities extends ModRegistry<EntityType<?>> {
    private static final Map<EntityType<?>, Tuple<Integer, Integer>> EGGS = new HashMap<>();
    public static final EntityType<SpiritEntity> SPIRIT = create(SpiritEntity::new, EntityClassification.AMBIENT, 1, 1, false, 0xB0C9FA, 0xCADBE7, "spirit");
    //public static final EntityType<SidheEntity> SIDHE = create(SidheEntity::new, EntityClassification.CREATURE, 0.3F, 0.5F, false, 0xEAF3B0, 0x82C45F, "sidhe");
    public static final EntityType<LadyGwenEntity> LADY_GWEN = create(LadyGwenEntity::new, EntityClassification.CREATURE, 1, 1.5F, false, 0xE4F3F9, 0x503D31, "lady_gwen");
    public static final EntityType<BlackPigEntity> BLACK_PIG = create(BlackPigEntity::new, EntityClassification.CREATURE, 1, 1, false, "black_pig");
    public static final EntityType<DullahanEntity> DULLAHAN = create(DullahanEntity::new, EntityClassification.CREATURE, 1, 1.5F, false, 0x503D31, 0xD8600F, "dullahan");
    public static final EntityType<BlackHorseEntity> BLACK_HORSE = create(BlackHorseEntity::new, EntityClassification.CREATURE, 1.5F, 1.6F, false, "black_horse");

    //todo fix spirit hit boxes, add configurable spawns

    private static <T extends Entity> EntityType<T> create(EntityType.IFactory<T> factoryIn, EntityClassification classificationIn, float width, float height, boolean velocity, int primary, int secondary, String name) {
        EntityType<T> type = create(factoryIn, classificationIn, width, height, velocity, name);
        EGGS.put(type, new Tuple<>(primary, secondary));
        return type;
    }

    /**
     * created a copy of the create method for entities without spawn eggs
     */
    private static <T extends Entity> EntityType<T> create(EntityType.IFactory<T> factoryIn, EntityClassification classificationIn, float width, float height, boolean velocity, String name) {
        EntityType<T> type = EntityType.Builder.create(factoryIn, classificationIn).size(width, height).setShouldReceiveVelocityUpdates(velocity).build(name);
        type.setRegistryName(name);
        return type;
    }

    public static void registerEggs(List<Item> registry) {
        final Item.Properties properties = new Item.Properties().group(ModItems.GROUP);
        EGGS.forEach((k, v) -> registry.add(new SpawnEggItem(k, v.getA(), v.getB(), properties).setRegistryName(Objects.requireNonNull(k.getRegistryName()).getPath() + "_spawn_egg")));
    }
}
