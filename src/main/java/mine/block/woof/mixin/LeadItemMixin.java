/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.mixin;

import net.minecraft.item.DyeableItem;
import net.minecraft.item.LeadItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeadItem.class)
public class LeadItemMixin implements DyeableItem {
}
