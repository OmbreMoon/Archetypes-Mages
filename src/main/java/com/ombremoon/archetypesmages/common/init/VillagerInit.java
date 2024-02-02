package com.ombremoon.archetypesmages.common.init;

import com.google.common.collect.ImmutableSet;
import com.ombremoon.archetypesmages.Constants;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VillagerInit {
    /**
     * TODO
     * SHAMAN CIRCLE - SHAMAN'S DRUM - SHAMAN
     * WOODLAND SHRINE - WOODLAND ALTAR - DRUID
     * WARLOCK'S HUT - HEARTH - WARLOCK
     * MAGE HIDEOUT - SHADOW ALTAR - SHADOW MAGE
     * HOLY TEMPLE - HOLY SYMBOL - CLERIC
     * */

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Constants.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Constants.MOD_ID);

    public static final RegistryObject<PoiType> SHAMAN_POI = POI_TYPES.register("shaman_poi", () -> new PoiType(ImmutableSet.copyOf(BlockInit.DRUM.get().getStateDefinition().getPossibleStates()), 1, 1));
    public static final RegistryObject<PoiType> DRUID_POI = POI_TYPES.register("druid_poi", () -> new PoiType(ImmutableSet.copyOf(BlockInit.WOODLAND_ALTAR.get().getStateDefinition().getPossibleStates()), 1, 1));
    public static final RegistryObject<PoiType> WARLOCK_POI = POI_TYPES.register("warlock_poi", () -> new PoiType(ImmutableSet.copyOf(BlockInit.HEARTH.get().getStateDefinition().getPossibleStates()), 1, 1));
    public static final RegistryObject<PoiType> SHADOW_MAGE_POI = POI_TYPES.register("shadow_mage_poi", () -> new PoiType(ImmutableSet.copyOf(BlockInit.SHADOW_ALTAR.get().getStateDefinition().getPossibleStates()), 1, 1));
    public static final RegistryObject<PoiType> DIVINE_CLERIC_POI = POI_TYPES.register("divine_cleric_poi", () -> new PoiType(ImmutableSet.copyOf(BlockInit.DIVINE_SYMBOL.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final RegistryObject<VillagerProfession> SHAMAN = VILLAGER_PROFESSIONS.register("shaman", () -> new VillagerProfession("shaman",
            holder -> holder.get() == SHAMAN_POI.get(), holder -> holder.get() == SHAMAN_POI.get(),
            ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CLERIC));
    public static final RegistryObject<VillagerProfession> DRUID = VILLAGER_PROFESSIONS.register("druid", () -> new VillagerProfession("druid",
            holder -> holder.get() == DRUID_POI.get(), holder -> holder.get() == DRUID_POI.get(),
            ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CLERIC));
    public static final RegistryObject<VillagerProfession> WARLOCK = VILLAGER_PROFESSIONS.register("warlock", () -> new VillagerProfession("warlock",
            holder -> holder.get() == WARLOCK_POI.get(), holder -> holder.get() == WARLOCK_POI.get(),
            ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CLERIC));
    public static final RegistryObject<VillagerProfession> SHADOW_MAGE = VILLAGER_PROFESSIONS.register("shadow_mage", () -> new VillagerProfession("shadow_mage",
            holder -> holder.get() == SHADOW_MAGE_POI.get(), holder -> holder.get() == SHADOW_MAGE_POI.get(),
            ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CLERIC));
    public static final RegistryObject<VillagerProfession> DIVINE_CLERIC = VILLAGER_PROFESSIONS.register("divine_cleric", () -> new VillagerProfession("divine_cleric",
            holder -> holder.get() == DIVINE_CLERIC_POI.get(), holder -> holder.get() == DIVINE_CLERIC_POI.get(),
            ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CLERIC));

    public static void register(IEventBus modEventBus) {
        POI_TYPES.register(modEventBus);
        VILLAGER_PROFESSIONS.register(modEventBus);
    }
}
