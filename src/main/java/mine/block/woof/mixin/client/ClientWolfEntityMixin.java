/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

///*
// * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
// *
// * All Rights Reserved
// */
//
//package mine.block.woof.mixin.client;
//
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.entity.Tameable;
//import net.minecraft.entity.passive.WolfEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(WolfEntity.class)
//public abstract class ClientWolfEntityMixin implements Tameable {
//    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
//    public void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
//        if (player.isSneaking() && this.getOwnerUuid() == player.getUuid() && player.getStackInHand(hand).isEmpty() && player.world.isClient) {
////            MinecraftClient.getInstance().setScreen(new WolfManagerScreen((WolfEntity) (Object) this));
//            cir.setReturnValue(ActionResult.PASS);
//        }
//    }
//}
