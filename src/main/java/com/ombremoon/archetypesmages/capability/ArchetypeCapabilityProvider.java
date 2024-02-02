package com.ombremoon.archetypesmages.capability;

import com.ombremoon.archetypesmages.CommonClass;
import com.ombremoon.archetypesmages.Constants;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArchetypeCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<IArchetypeCapability> ARCHETYPE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final ResourceLocation ARCHETYPE_LOCATION = CommonClass.customLocation("archetype");

    private IArchetypeCapability archetypeCapability = new ArchetypeCapabilityHandler(100);
    private final LazyOptional<IArchetypeCapability> optional = LazyOptional.of(() -> archetypeCapability);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ARCHETYPE_CAPABILITY.orEmpty(cap, this.optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.archetypeCapability.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.archetypeCapability.deserializeNBT(nbt);
    }
}
