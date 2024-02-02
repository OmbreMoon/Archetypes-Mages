package com.ombremoon.archetypesmages.util;

import com.ombremoon.archetypesmages.capability.ArchetypeCapabilityProvider;
import com.ombremoon.archetypesmages.common.init.SpellInit;
import com.ombremoon.archetypesmages.object.custom.spell.AbstractSpell;
import com.ombremoon.archetypesmages.object.custom.spell.SpellInstance;
import com.ombremoon.archetypesmages.object.custom.spell.SpellType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class ArchetypeUtil {

    public static SpellType<?> getSpellByName(ResourceLocation resourceLocation) {
        for (RegistryObject<SpellType<?>> registryObject : SpellInit.SPELL_TYPE.getEntries()) {
            if (registryObject.getId().equals(resourceLocation)) {
                return registryObject.get();
            }
        }
        return null;
    }

    public static Map<AbstractSpell, SpellInstance> getActiveGrimoireSpells(Player player) {
        return player.getCapability(ArchetypeCapabilityProvider.ARCHETYPE_CAPABILITY).orElseThrow(NullPointerException::new).getActiveSpells();
    }

    public static CompoundTag storeAbility(SpellType<?> spellType, String tagKey) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString(tagKey, spellType.getResourceLocation().toString());
        return compoundTag;
    }

    public static ResourceLocation getGrimoireAbilityId(CompoundTag compoundTag, String tagKey) {
        return ResourceLocation.tryParse(compoundTag.getString(tagKey));
    }
}
