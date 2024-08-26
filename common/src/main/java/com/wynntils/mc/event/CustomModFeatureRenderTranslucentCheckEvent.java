/*
 * Copyright © Wynntils 2024.
 * This file is released under LGPLv3. See LICENSE for full license details.
 */
package com.wynntils.mc.event;

import net.minecraft.world.entity.LivingEntity;

/**
 * Currently fired from {@link com.wynntils.mc.mixin.compat.CustomLayerFeatureRendererMixin} checks whether an
 * entity layer from other mods should be rendered translucent or not
 */
public class CustomModFeatureRenderTranslucentCheckEvent extends LivingEntityRenderTranslucentCheckEvent {
    public CustomModFeatureRenderTranslucentCheckEvent(boolean translucent, LivingEntity entity, float translucence) {
        super(translucent, entity, translucence);
    }
}
