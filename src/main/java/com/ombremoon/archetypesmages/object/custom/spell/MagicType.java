package com.ombremoon.archetypesmages.object.custom.spell;

public enum MagicType {
    ELEMENTAL("Elemental"),
    ALTERATION("Alteration");

    private final String magicName;

    MagicType(String magicName) {
        this.magicName = magicName;
    }

    public String getMagicName() {
        return this.magicName;
    }
}
