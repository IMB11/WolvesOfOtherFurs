/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof.entity;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class WolfVariantTracker implements TrackedDataHandler<Identifier> {
    public static final WolfVariantTracker VARIANT_TRACKER = new WolfVariantTracker();
    public static final TrackedData<Identifier> VARIANT_TYPE = DataTracker.registerData(WolfEntity.class, VARIANT_TRACKER);

    @Override
    public void write(PacketByteBuf buf, Identifier value) {
        buf.writeIdentifier(value);
    }

    @Override
    public Identifier read(PacketByteBuf buf) {
        return buf.readIdentifier();
    }

    @Override
    public Identifier copy(Identifier value) {
        return new Identifier(value.getNamespace(), value.getPath());
    }
}
