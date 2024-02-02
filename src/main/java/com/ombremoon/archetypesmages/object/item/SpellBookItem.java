package com.ombremoon.archetypesmages.object.item;

import com.ombremoon.archetypesmages.object.custom.spell.SpellType;
import net.minecraft.world.item.Item;

public class SpellBookItem extends Item {
    public SpellBookItem(SpellType<?> spellType, Properties properties) {
        super(properties);
    }
}
