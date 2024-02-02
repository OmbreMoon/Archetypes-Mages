package com.ombremoon.archetypesmages.object.custom.spell;

import com.ombremoon.archetypesmages.Constants;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class SpellInstance {
    public static final int INFINITE = -1;
    private final SpellType<?> spellType;
    private int duration;
    private int levelBonus;
    @Nullable
    private SpellInstance hiddenSpell;

    public SpellInstance(SpellType<?> spellType) {
        this(spellType, spellType.getSupplier().getDuration());
    }

    public SpellInstance(SpellType<?> spellType, int duration) {
        this(spellType, duration, 0);
    }

    public SpellInstance(SpellType<?> spellType, int duration, int levelBonus) {
        this(spellType, duration, levelBonus, null);
    }

    public SpellInstance(SpellType<?> spellType, int duration, int levelBonus, @Nullable SpellInstance hiddenSpell) {
        this.spellType = spellType;
        this.duration = duration;
        this.levelBonus = levelBonus;
        this.hiddenSpell = hiddenSpell;
    }

    public SpellInstance(SpellInstance spellInstance) {
        this.spellType = spellInstance.spellType;
        this.setDetailsFrom(spellInstance);
    }

    void setDetailsFrom(SpellInstance spellInstance) {
        this.duration = spellInstance.duration;
        this.levelBonus = spellInstance.levelBonus;
    }

    public boolean updateSpell(SpellInstance spellInstance) {
        if (this.hiddenSpell != spellInstance.hiddenSpell) {
            Constants.LOG.warn("This method should only be called for matching spells!");
        }

        boolean flag = false;
        if (spellInstance.levelBonus > this.levelBonus) {
            if (spellInstance.isShorterDurationThan(this)) {
                SpellInstance grimoireSpellInstance = this.hiddenSpell;
                this.hiddenSpell = new SpellInstance(this);
                this.hiddenSpell.hiddenSpell = grimoireSpellInstance;
            }

            this.levelBonus = spellInstance.levelBonus;
            this.duration = spellInstance.duration;
            flag = true;
        } else if (this.isShorterDurationThan(spellInstance)) {
            if (spellInstance.levelBonus == this.levelBonus) {
                this.duration = spellInstance.duration;
                flag = true;
            } else if (this.hiddenSpell == null) {
                this.hiddenSpell = new SpellInstance(spellInstance);
            } else {
                this.hiddenSpell.updateSpell(spellInstance);
            }
        }

        return flag;
    }

    /*    public boolean activateSpellEffect(GrimoireSpellInstance grimoireSpellInstance, Player player) {
        GrimoireSpell grimoireSpell = grimoireSpellInstance.getGrimoireSpell();
        GrimoireSpellInstance currentSpellInstance = GrimoireUtil.getActiveGrimoireSpells(player).get(grimoireSpell);
        if (currentSpellInstance == null) {
            GrimoireUtil.activateGrimoireSpells(player, grimoireSpell, grimoireSpellInstance);
            grimoireSpell.onSpellActivated(grimoireSpellInstance, player);
            return true;
        } else if (currentSpellInstance.updateSpell(grimoireSpellInstance)) {
            currentSpellInstance.getGrimoireSpell().onSpellUpdate(currentSpellInstance, true, player);
            return true;
        } else {
            return false;
        }
    }*/

    private boolean isShorterDurationThan(SpellInstance pOther) {
        return !this.isInfiniteDuration() && (this.duration < pOther.duration || pOther.isInfiniteDuration());
    }

    public boolean isInfiniteDuration() {
        return this.duration == INFINITE;
    }

    public boolean endsWithin(int pDuration) {
        return !this.isInfiniteDuration() && this.duration <= pDuration;
    }

    public int mapDuration(Int2IntFunction pMapper) {
        return !this.isInfiniteDuration() && this.duration != 0 ? pMapper.applyAsInt(this.duration) : this.duration;
    }

    public AbstractSpell getSpellType() {
        return this.spellType.getSupplier();
    }

    public int getDuration() {
        return this.duration;
    }

    public boolean tickSpellEffect(Player player, Runnable onExpired) {
        if (this.hasRemainingDuration()) {
            int i = this.isInfiniteDuration() ? player.tickCount : this.duration;
            if (this.getSpellType().isDurationEffectTick(i)) {
                this.applySpellEffect(player);
            }

            this.tickDownDuration();
            if (this.duration == 0 && this.hiddenSpell != null) {
                this.setDetailsFrom(this.hiddenSpell);
                this.hiddenSpell = this.hiddenSpell.hiddenSpell;
                onExpired.run();
            }
        }
        return this.hasRemainingDuration();
    }

    private boolean hasRemainingDuration() {
        return this.isInfiniteDuration() || this.duration > 0;
    }

    private int tickDownDuration() {
        if (this.hiddenSpell != null) {
            this.hiddenSpell.tickDownDuration();
        }

        return this.duration = this.mapDuration((currentDuration) -> {
            return currentDuration - 1;
        });
    }

    public void applySpellEffect(Player player) {
        if (this.hasRemainingDuration()) {
            this.getSpellType().applySpellEffect(player);
        }
    }

    public String getDescriptionId() {
        return this.spellType.getSupplier().getDescriptionId();
    }

    @Override
    public boolean equals(Object pOther) {
        if (this == pOther) {
            return true;
        } else if (!(pOther instanceof SpellInstance)) {
            return false;
        } else {
            SpellInstance spellInstance = (SpellInstance) pOther;
            return this.duration == spellInstance.duration && this.spellType.equals(spellInstance.spellType);
        }
    }

    @Override
    public int hashCode() {
        int i = this.spellType.hashCode();
        i = 31 * i + this.duration;
        return 31 * i;
    }
}
