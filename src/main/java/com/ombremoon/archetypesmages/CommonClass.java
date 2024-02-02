package com.ombremoon.archetypesmages;

import com.ombremoon.archetypesmages.common.init.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class CommonClass {

    public static void init(IEventBus modEventBus) {
        BlockInit.register(modEventBus);
        ItemInit.register(modEventBus);
        EntityInit.register(modEventBus);
        VillagerInit.register(modEventBus);
        SpellInit.init(modEventBus);
    }

    public static ResourceLocation customLocation(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }
}
