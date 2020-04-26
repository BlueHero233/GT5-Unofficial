package com.github.technus.tectech.mechanics.structure;


import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

public interface IHatchAdder<T> {
    /**
     * Callback to add hatch
     * @param iGregTechTileEntity hatch
     * @param aShort requested texture index, or null if not...
     * @return managed to add hatch (structure still valid)
     */
    boolean apply(T t,IGregTechTileEntity iGregTechTileEntity, Short aShort);
}
