/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof.mixin;

import net.minecraft.item.DyeableItem;
import net.minecraft.item.LeadItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeadItem.class)
public class LeadItemMixin implements DyeableItem {
}
