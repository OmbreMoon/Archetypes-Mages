package com.ombremoon.archetypesmages.capability;

import com.ombremoon.archetypesmages.object.custom.spell.AbstractSpell;
import com.ombremoon.archetypesmages.object.custom.spell.SpellInstance;
import com.ombremoon.archetypesmages.object.custom.spell.SpellType;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.LinkedHashSet;
import java.util.Map;

@AutoRegisterCapability
public interface IArchetypeCapability extends INBTSerializable<CompoundTag> {

    void updateSpellSet(SpellType<?> grimoireAbility);

    LinkedHashSet<SpellType<?>> getSpellSet();

    Map<AbstractSpell, SpellInstance> getActiveSpells();

    int increaseMana(int inc, boolean sim);

    int decreaseMana(int dec, boolean sim);

    void setMana(int energy);

    int getMana();

    int getManaCapacity();

    int setManaCapacity(int manaCapacity);

    boolean isInCombatMode();

    void setCombatModeStatus(boolean inCombatMode);
}
