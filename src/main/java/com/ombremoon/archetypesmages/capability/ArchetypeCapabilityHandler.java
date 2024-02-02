package com.ombremoon.archetypesmages.capability;

import com.ombremoon.archetypesmages.object.custom.spell.AbstractSpell;
import com.ombremoon.archetypesmages.object.custom.spell.SpellInstance;
import com.ombremoon.archetypesmages.object.custom.spell.SpellType;
import com.ombremoon.archetypesmages.util.ArchetypeUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class ArchetypeCapabilityHandler implements IArchetypeCapability {
    private final LinkedHashSet<SpellType<?>> spellSet = new LinkedHashSet<>();
    private int currentMana;
    private int manaCapacity;
    private boolean inCombatMode;
    private final Map<AbstractSpell, SpellInstance> activeSpells = new HashMap<>();
    private static final String NBT_GRIMOIRE_KEY = "grimoire";
    private static final String NBT_COMBAT_KEY = "combat";
    private static final String NBT_MANA_KEY = "mana";

    public ArchetypeCapabilityHandler(int manaCapacity) {
        this(manaCapacity, 0);
    }

    public ArchetypeCapabilityHandler(int manaCapacity, int currentMana) {
        this.manaCapacity = manaCapacity;
        this.currentMana = Mth.clamp(currentMana, 0, manaCapacity);
    }

    @Override
    public void updateSpellSet(SpellType<?> grimoireAbility) {
        this.spellSet.add(grimoireAbility);
    }

    @Override
    public LinkedHashSet<SpellType<?>> getSpellSet() {
        return this.spellSet;
    }

    @Override
    public Map<AbstractSpell, SpellInstance> getActiveSpells() {
        return this.activeSpells;
    }

    @Override
    public int increaseMana(int inc, boolean sim) {
        int manaIncreased = Math.min(manaCapacity - currentMana, inc);
        if (!sim)
            currentMana += manaIncreased;
        return manaIncreased;
    }

    @Override
    public int decreaseMana(int dec, boolean sim) {
        int manaDecreased = Math.min(currentMana, dec);
        if (!sim)
            currentMana -= manaDecreased;
        return manaDecreased;
    }

    @Override
    public void setMana(int manaAmount) {
        this.currentMana = manaAmount;
    }

    @Override
    public int getMana() {
        return this.currentMana;
    }

    @Override
    public int getManaCapacity() {
        return this.manaCapacity;
    }

    @Override
    public int setManaCapacity(int manaCapacity) {
        return this.manaCapacity = manaCapacity;
    }

    @Override
    public boolean isInCombatMode() {
        return this.inCombatMode;
    }

    @Override
    public void setCombatModeStatus(boolean inCombatMode) {
        this.inCombatMode = inCombatMode;
    }

    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag tag = new CompoundTag();
        final ListTag listTag = new ListTag();
        tag.putInt(NBT_MANA_KEY, this.currentMana);
        tag.putBoolean(NBT_COMBAT_KEY, this.inCombatMode);
        for (SpellType<?> grimoireAbility : spellSet) {
            listTag.add(ArchetypeUtil.storeAbility(grimoireAbility, NBT_GRIMOIRE_KEY));
        }
        tag.put(NBT_GRIMOIRE_KEY, listTag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.currentMana = nbt.getInt(NBT_MANA_KEY);
        this.inCombatMode = nbt.getBoolean(NBT_COMBAT_KEY);
        ListTag listTag = nbt.getList(NBT_GRIMOIRE_KEY, 10);

        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag compoundTag = listTag.getCompound(i);
            this.spellSet.add(ArchetypeUtil.getSpellByName(ArchetypeUtil.getGrimoireAbilityId(compoundTag, NBT_GRIMOIRE_KEY)));
        }
    }
}
