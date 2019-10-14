package com.github.mnesikos.samhain;

import com.github.mnesikos.samhain.init.ModConfiguration;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = Samhain.MOD_ID)
public class Samhain {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "samhain";

    //removed a bunch of stuff from here since they were useless, including empty registries and the proxies

    public Samhain() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfiguration.SPEC);
    }
}
