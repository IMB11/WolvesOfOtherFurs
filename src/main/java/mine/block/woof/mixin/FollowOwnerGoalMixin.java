/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof.mixin;

import mine.block.woof.entity.WoofWolf;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.passive.TameableEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FollowOwnerGoal.class)
public class FollowOwnerGoalMixin {
    @Shadow @Final private TameableEntity tameable;

    @Inject(method = "canStart", cancellable = true, at = @At("HEAD"))
    public void injectCanStart(CallbackInfoReturnable<Boolean> cir) {
        if(this.tameable instanceof WoofWolf wolf) {
            WoofWolf.FollowMode mode = wolf.getFollowMode();
            if (mode == WoofWolf.FollowMode.STAY) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "shouldContinue", cancellable = true, at = @At("HEAD"))
    public void injectShouldContinue(CallbackInfoReturnable<Boolean> cir) {
        if(this.tameable instanceof WoofWolf wolf) {
            WoofWolf.FollowMode mode = wolf.getFollowMode();
            if (mode == WoofWolf.FollowMode.STAY) {
                cir.setReturnValue(false);
            }
        }
    }
}
