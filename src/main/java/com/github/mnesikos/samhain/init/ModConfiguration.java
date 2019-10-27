package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.Samhain;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class ModConfiguration {

    public static final ForgeConfigSpec SPEC;
    public static final ModConfiguration INSTANCE;
    public final ForgeConfigSpec.ConfigValue<List<String>> spiritTypes;
    //public final ForgeConfigSpec.BooleanValue changeOtherworldWater;

    private ModConfiguration(ForgeConfigSpec.Builder builder) {
        spiritTypes = builder.comment("The ageable entities that can be spawned as a Spirit(Uses domain 'minecraft' by default)").translation(Samhain.MOD_ID + ".spiritTypes").define("spiritTypes", Arrays.asList("cow", "wolf", "parrot", "fox", "panda", "villager", "pig", "sheep", "chicken"));
        //changeOtherworldWater = builder.comment("Change the color of water in the Otherworld using a coremod").translation(Samhain.MOD_ID + ".changeOtherworldWater").define("changeOtherworldWater", true);
    }

    static {
        Pair<ModConfiguration, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(ModConfiguration::new);
        SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }
}
