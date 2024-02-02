package com.ombremoon.archetypesmages.object.custom.spell;

import com.ombremoon.archetypesmages.common.init.SpellInit;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public abstract class AbstractSpell {
    private final SpellType<?> spellType;
    private final MagicType magicType;
    private SpellLevel spellLevel;
    private final int duration;
    private String descriptionId;

    public AbstractSpell(SpellType<?> spellType, MagicType magicType, SpellLevel spellLevel) {
        this(spellType, magicType, spellLevel, 1);
    }

    public AbstractSpell(SpellType<?> spellType, MagicType magicType, SpellLevel spellLevel, int duration) {
        this.spellType = spellType;
        this.magicType = magicType;
        this.spellLevel = spellLevel;
        this.duration = duration;
    }

    public SpellType<?> getSpellType() {
        return this.spellType;
    }

    public ResourceLocation getSpellLocation() {
        return SpellInit.REGISTRY.get().getKey(this.spellType);
    }

    public MagicType getMagicType() {
        return this.magicType;
    }

    public int getDuration() {
        return this.duration;
    }

    public boolean isInstantSpell() {
        return true;
    }

    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("effect", SpellInit.REGISTRY.get().getKey(this.getSpellType()));
        }

        return this.descriptionId;
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    public Component getSpellName() {
        return Component.translatable(this.getDescriptionId());
    }

    public abstract void applySpellEffect(Player player);

    public void onSpellActivated(SpellInstance abilityInstance, Player player) {

    }

    public void onSpellUpdate(SpellInstance abilityInstance, boolean isForced, Player player) {

    }

    public void onSpellRemoved(SpellInstance abilityInstance) {

    }

    public boolean isDurationEffectTick(int pDuration) {
        return true;
    }

    public enum SpellLevel {
        NOVICE,
        APPRENTICE,
        ADEPT,
        EXPERT,
        MASTER;

        SpellLevel() {

        }
    }
}
