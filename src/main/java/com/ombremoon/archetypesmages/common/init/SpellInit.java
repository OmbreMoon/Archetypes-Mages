package com.ombremoon.archetypesmages.common.init;

import com.ombremoon.archetypesmages.ArchetypesMages;
import com.ombremoon.archetypesmages.CommonClass;
import com.ombremoon.archetypesmages.Constants;
import com.ombremoon.archetypesmages.object.custom.spell.AbstractSpell;
import com.ombremoon.archetypesmages.object.custom.spell.MagicType;
import com.ombremoon.archetypesmages.object.custom.spell.SpellType;
import com.ombremoon.archetypesmages.object.custom.spell.alteration.AlarmSpell;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SpellInit {
    public static final DeferredRegister<SpellType<?>> SPELL_TYPE = DeferredRegister.create(CommonClass.customLocation("spells"), Constants.MOD_ID);
    public static final Supplier<IForgeRegistry<SpellType<?>>> REGISTRY = SPELL_TYPE.makeRegistry(RegistryBuilder::new);

    public static RegistryObject<SpellType<?>> ALARM_SPELL = registerSpell("alarm", () -> new AlarmSpell(MagicType.ALTERATION));

    private static <T extends AbstractSpell> RegistryObject<SpellType<?>> registerSpell(String name, Supplier<T> supplier) {
        return SPELL_TYPE.register(name, () -> new SpellType<>(CommonClass.customLocation(name), supplier));
    }

    public static void init(IEventBus modEventBus) {
        SPELL_TYPE.register(modEventBus);
    }

}
