package mine.block.woof.mixin;

import net.minecraft.item.DyeableItem;
import net.minecraft.item.LeadItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeadItem.class)
public class LeadItemMixin implements DyeableItem {
}
