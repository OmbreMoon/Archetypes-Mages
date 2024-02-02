package com.ombremoon.archetypesmages.object.custom.spell.alteration;

import com.ombremoon.archetypesmages.common.init.SpellInit;
import com.ombremoon.archetypesmages.object.custom.spell.AbstractSpell;
import com.ombremoon.archetypesmages.object.custom.spell.MagicType;
import net.minecraft.world.entity.player.Player;

public class AlarmSpell extends AbstractSpell {
    public AlarmSpell(MagicType magicType) {
        super(SpellInit.ALARM_SPELL.get(), magicType, SpellLevel.NOVICE);
    }

    @Override
    public void applySpellEffect(Player player) {

    }
}
