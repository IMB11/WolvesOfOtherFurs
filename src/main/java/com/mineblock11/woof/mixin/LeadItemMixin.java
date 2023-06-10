/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package com.mineblock11.woof.mixin;

import net.minecraft.item.DyeableItem;
import net.minecraft.item.LeadItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeadItem.class)
public class LeadItemMixin implements DyeableItem {
}
