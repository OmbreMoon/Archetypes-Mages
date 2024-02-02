package com.ombremoon.archetypesmages.common.init;

import com.ombremoon.archetypesmages.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register(Constants.MOD_ID, () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(Items.DIRT))
            .title(Component.translatable("itemGroup." + Constants.MOD_ID + ".tab"))
            .build());



    public static Item.Properties getItemProperties() {
        return new Item.Properties();
    }

    public static void registerCustomTabObjects(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == ItemInit.TAB.get()) {
            ITEMS.getEntries().forEach((registryObject) -> event.accept(registryObject.get()));
        }
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(EventPriority.NORMAL, false, BuildCreativeModeTabContentsEvent.class, ItemInit::registerCustomTabObjects);
    }
}
