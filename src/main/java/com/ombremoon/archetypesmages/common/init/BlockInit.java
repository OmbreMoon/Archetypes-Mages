package com.ombremoon.archetypesmages.common.init;

import com.ombremoon.archetypesmages.Constants;
import com.ombremoon.archetypesmages.object.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

    public static final RegistryObject<Block> DRUM = registerBlock("drum", () -> new DrumBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.WOOD).strength(0.8F).ignitedByLava()));
    public static final RegistryObject<Block> DIVINE_SYMBOL = registerBlock("divine_symbol", () -> new DivineSymbolBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.WOOD).strength(0.8F).ignitedByLava()));
    public static final RegistryObject<Block> HEARTH = registerBlock("hearth", () -> new HearthBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.WOOD).strength(0.8F).ignitedByLava()));
    public static final RegistryObject<Block> WOODLAND_ALTAR = registerBlock("woodland_altar", () -> new WoodlandAltarBlock(BlockBehaviour.Properties.copy(Blocks.DIORITE)));
    public static final RegistryObject<Block> SHADOW_ALTAR = registerBlock("shadow_altar", () -> new ShadowAltarBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static RegistryObject<Block> registerBlock(String name, Supplier<Block> block) {
        var registryObject = BLOCKS.register(name, block);
        ItemInit.ITEMS.register(name, () -> new BlockItem(registryObject.get(), new Item.Properties()));
        return registryObject;
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }

}
