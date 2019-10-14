package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.Samhain;
import com.google.common.collect.Lists;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

abstract class ModRegistry<T extends IForgeRegistryEntry<T>> {

    @SuppressWarnings({"unchecked", "WeakerAccess"})
    void init(IForgeRegistry<T> registry) {
        List<T> list = new ArrayList<>();
        try {
            for (Field field : getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(registry.getRegistrySuperType().isInstance(field.get(null))) list.add((T) field.get(null));
            }
        } catch (IllegalAccessException e) {
            Samhain.LOGGER.fatal("Failed to register Samhain " + registry.getRegistrySuperType().getSimpleName().toLowerCase() + "s", e);
        }
        list.sort((t, t1) -> {
            String s1 = Objects.requireNonNull(t.getRegistryName()).getPath();
            String s2 = Objects.requireNonNull(t1.getRegistryName()).getPath();
            String[] a1 = s1.split("_");
            String[] a2 = s2.split("_");
            if (s1.contains("_") && s2.contains("_")) return (a2[1] + '_' + a2[0]).compareTo(a1[1] + '_' + a1[0]);
            else if (s1.contains("_")) s1 = a1[1] + '_' + a1[0];
            else if (s2.contains("_")) s2 = a2[1] + '_' + a2[0];
            return s2.compareTo(s1);
        });
        for(T t : Lists.reverse(list)) registry.register(t);
    }

    static <V extends IForgeRegistryEntry<V>> void register(RegistryEvent.Register<V> event, Supplier<? extends ModRegistry<V>> registry) {
        registry.get().init(event.getRegistry());
    }
}
