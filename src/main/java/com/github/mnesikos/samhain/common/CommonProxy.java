package com.github.mnesikos.samhain.common;

import com.github.mnesikos.samhain.IProxy;
import net.minecraft.world.World;

public class CommonProxy implements IProxy {
    @Override
    public void init() {

    }

    @Override
    public World getClientWorld() {
        throw new IllegalStateException("Only run this on the client.");
    }
}
