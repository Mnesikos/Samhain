package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.common.entity.BlackPigEntity;
import com.github.mnesikos.samhain.common.entity.SidheEntity;
import com.github.mnesikos.samhain.common.entity.SpiritEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModEntities {
    public static final List<EntityType<?>> LIST = new ArrayList<>();
    private static final List<EggEntry> EGGS = new ArrayList<>();
    public static final EntityType<SpiritEntity> SPIRIT = create(SpiritEntity::new, EntityClassification.AMBIENT, 1, 1, 0, 0, "spirit");
    public static final EntityType<SidheEntity> SIDHE = create(SidheEntity::new, EntityClassification.MONSTER, 1, 1, 0, 0, "sidhe");
    public static final EntityType<BlackPigEntity> BLACK_PIG = create(BlackPigEntity::new, EntityClassification.CREATURE, 1, 1, 0 , 0, "black_pig");

    //todo set the egg colors and sizes for all mobs
    private static <T extends Entity> EntityType<T> create(EntityType.IFactory<T> factoryIn, EntityClassification classificationIn, float width, float height, int primary, int secondary, String name) {
        EntityType<T> type = EntityType.Builder.create(factoryIn, classificationIn).size(width, height).setShouldReceiveVelocityUpdates(false).build(name);
        type.setRegistryName(name);
        LIST.add(type);
        EGGS.add(new EggEntry(type, primary, secondary));
        return type;
    }

    static void registerEggs() {
        for(EggEntry egg : EGGS) ModItems.LIST.add(new SpawnEggItem(egg.type, egg.primary, egg.secondary, EggEntry.properties).setRegistryName(Objects.requireNonNull(egg.type.getRegistryName()).getPath() + "_spawn_egg"));
    }

    private static class EggEntry {
        private static final Item.Properties properties = new Item.Properties().group(ModItems.GROUP);
        private final EntityType<?> type;
        private final int primary;
        private final int secondary;

        private EggEntry(EntityType<?> type, int primary, int secondary) {
            this.type = type;
            this.primary = primary;
            this.secondary = secondary;
        }
    }
}
