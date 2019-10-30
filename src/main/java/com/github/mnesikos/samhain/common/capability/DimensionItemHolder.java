package com.github.mnesikos.samhain.common.capability;

import net.minecraft.nbt.ListNBT;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DimensionItemHolder {
    public Map<UUID, ListNBT> inventories = new HashMap<>();
}
