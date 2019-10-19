package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.Samhain;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public abstract class ModRegistry<T extends IForgeRegistryEntry<T>> {

    private static final Map<Class<? extends ModRegistry>, ModRegistry> REGISTRIES = new HashMap<>();
    public final List<T> list = new ArrayList<>();

    public void init(IForgeRegistry<T> registry) {
        for (Field field : getClass().getFields()) if(registry.getRegistrySuperType().isAssignableFrom(field.getType())) {
            try {
                list.add((T) field.get(null));
            } catch (IllegalAccessException e) {
                Samhain.LOGGER.fatal("Failed to register Samhain " + registry.getRegistrySuperType().getSimpleName() + " '" + field.getName().toLowerCase() + "'", e);
            }
        }

        //uncomment this if you want the registered objects to be sorted by name
        /*list.sort((t, t1) -> {
            //gets names and splits by _
            String s1 = Objects.requireNonNull(t.getRegistryName()).getPath();
            String s2 = Objects.requireNonNull(t1.getRegistryName()).getPath();
            String[] a1 = s1.split("_");
            String[] a2 = s2.split("_");
            //switches names around, e.g wooden_arrow and iron_arrow to arrow_wooden and arrow_iron, so it can be sorted better
            if (s1.contains("_") && s2.contains("_")) return (a2[1] + '_' + a2[0]).compareTo(a1[1] + '_' + a1[0]);
            else if (s1.contains("_")) s1 = a1[1] + '_' + a1[0];
            else if (s2.contains("_")) s2 = a2[1] + '_' + a2[0];
            //returns final result
            return s1.compareTo(s2);
        });*/

        for(T t : list) registry.register(t);
    }

    public static <T extends IForgeRegistryEntry<T>> ModRegistry<T> make(Supplier<? extends ModRegistry<T>> type) {
        ModRegistry<T> instance = type.get();
        REGISTRIES.put(instance.getClass(), type.get());
        return instance;
    }

    public static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, Supplier<? extends ModRegistry<T>> type) {
        make(type).init(registry);
    }
}
