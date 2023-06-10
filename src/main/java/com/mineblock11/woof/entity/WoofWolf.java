/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package com.mineblock11.woof.entity;

public interface WoofWolf {
    enum FollowMode {
        FOLLOW,
        STAY
    }

    FollowMode getFollowMode();
}
