package mine.block.woof.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mine.block.woof.Woof;
import mine.block.woof.block.WoofBlocks;
import mine.block.woof.item.DyeableLeadItem;
import mine.block.woof.item.WoofItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LeadItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends Entity {
    @Shadow
    @Nullable
    private Entity holdingEntity;

    @Shadow
    @Nullable
    private NbtCompound leashNbt;

    @Unique
    private ItemStack leashItemStack;

    protected MobEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyExpressionValue(method = "interactWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    public boolean modifyInteractWithItemHardcoded(boolean original, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand).copy();
        if(original || itemStack.isOf(WoofItems.DYEABLE_LEAD_ITEM)) {
            leashItemStack = itemStack;
            return true;
        }
        return false;
    }

    /**
     * @author mineblock11
     * @reason Stupidly hardcoded
     */
    @Overwrite
    public void detachLeash(boolean sendPacket, boolean dropItem) {
        if (holdingEntity != null) {
            holdingEntity = null;
            leashNbt = null;
            if (!this.world.isClient && dropItem) {
                this.dropStack(leashItemStack);
            }

            leashItemStack = null;

            if (!this.world.isClient && sendPacket && this.world instanceof ServerWorld) {
                ((ServerWorld) this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityAttachS2CPacket(this, null));
            }
        }
    }

    /**
     * @author mineblock11
     * @reason Hardcoded
     */
    @Overwrite
    public void attachLeash(Entity entity, boolean sendPacket) {
        this.holdingEntity = entity;
        this.leashNbt = null;
        if (!this.world.isClient && sendPacket && this.world instanceof ServerWorld) {
            ((ServerWorld) this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityAttachS2CPacket(this, this.holdingEntity));
        }

        if (this.hasVehicle()) {
            this.stopRiding();
        }

    }

    @Shadow
    protected abstract void initDataTracker();

    @Shadow
    public abstract void readCustomDataFromNbt(NbtCompound nbt);

    @Shadow
    public abstract void writeCustomDataToNbt(NbtCompound nbt);
}
