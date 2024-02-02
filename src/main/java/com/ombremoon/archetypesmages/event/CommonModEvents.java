package com.ombremoon.archetypesmages.event;

import com.ombremoon.archetypesmages.Constants;
import com.ombremoon.archetypesmages.capability.ArchetypeCapabilityProvider;
import com.ombremoon.archetypesmages.common.init.BlockInit;
import com.ombremoon.archetypesmages.common.init.VillagerInit;
import com.ombremoon.archetypesmages.object.custom.spell.AbstractSpell;
import com.ombremoon.archetypesmages.object.custom.spell.SpellInstance;
import com.ombremoon.archetypesmages.util.ArchetypeUtil;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class CommonModEvents {

    @SubscribeEvent
    public static void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(ArchetypeCapabilityProvider.ARCHETYPE_CAPABILITY).isPresent()) {
                ArchetypeCapabilityProvider provider = new ArchetypeCapabilityProvider();
                event.addCapability(ArchetypeCapabilityProvider.ARCHETYPE_LOCATION, provider);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        event.getOriginal().getCapability(ArchetypeCapabilityProvider.ARCHETYPE_CAPABILITY).ifPresent(h -> {
            var storedMana = h.getMana();
            event.getEntity().getCapability(ArchetypeCapabilityProvider.ARCHETYPE_CAPABILITY).ifPresent(b -> {
                b.setMana(storedMana);
            });
        });
    }

    @SubscribeEvent
    public static void onAbilityTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Iterator<AbstractSpell> iterator = ArchetypeUtil.getActiveGrimoireSpells(player).keySet().iterator();

        try {
            while (iterator.hasNext()) {
                AbstractSpell grimoireSpell = iterator.next();
                SpellInstance grimoireSpellInstance = ArchetypeUtil.getActiveGrimoireSpells(player).get(grimoireSpell);
                if (!grimoireSpellInstance.tickSpellEffect(player, () -> {
                    grimoireSpell.onSpellUpdate(grimoireSpellInstance, true, player);
                })) {
                    if (!player.level().isClientSide) {
                        iterator.remove();
                        grimoireSpell.onSpellRemoved(grimoireSpellInstance);
                    }
                } else if (grimoireSpellInstance.getDuration() % 600 == 0) {
                    grimoireSpell.onSpellUpdate(grimoireSpellInstance, false, player);
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    @SubscribeEvent
    public static void onVillagerTrade(VillagerTradesEvent event) {
        if (event.getType() == VillagerInit.SHAMAN.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> villagerTrades = event.getTrades();

            villagerTrades.get(1).add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 6),
                    new ItemStack(BlockInit.DRUM.get(), 1),
                    2, 8, 0.02f)));
        }
    }
}
