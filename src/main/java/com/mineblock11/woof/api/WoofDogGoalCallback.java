/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package com.mineblock11.woof.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.passive.WolfEntity;

/**
 * An event callback that allows you to register custom goals to wolves.
 */
public interface WoofDogGoalCallback {
    Event<WoofDogGoalCallback> EVENT = EventFactory.createArrayBacked(WoofDogGoalCallback.class, (listeners) -> (goalSelector, targetSelector, wolfEntity) -> {
        for (WoofDogGoalCallback listener : listeners) {
            listener.registerGoal(goalSelector, targetSelector, wolfEntity);
        }
    });

    void registerGoal(GoalSelector goalSelector, GoalSelector targetSelector, WolfEntity wolfEntity);
}
