package mine.block.woof.client.gui;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.*;
import io.wispforest.owo.ui.core.*;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WolfManagerScreen extends BaseOwoScreen<FlowLayout> {
    private final WolfEntity target;

    public WolfManagerScreen(WolfEntity target) {
        this.target = target;
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        String ownerName = Objects.requireNonNull(this.target.getOwner()).getEntityName();
        String collarColor = this.target.getCollarColor().getName();
        collarColor = StringUtils.capitalize(collarColor);

        rootComponent.surface(Surface.VANILLA_TRANSLUCENT);
        rootComponent.child(Containers.draggable(Sizing.content(), Sizing.content(), Containers.verticalFlow(Sizing.content(), Sizing.content())
                .child(Components.label(Text.literal("Preview")).horizontalTextAlignment(HorizontalAlignment.CENTER))
                .child(Components.entity(Sizing.fixed(128), target).scaleToFit(true).allowMouseRotation(true).margins(Insets.of(5)))
                .child(Components.label(Text.literal("Statistics").formatted(Formatting.UNDERLINE)).horizontalTextAlignment(HorizontalAlignment.CENTER).margins(Insets.of(5, 2, 0, 0)))
                .child(Components.label(Text.literal("Owner - " + ownerName)))
                .child(Components.label(Text.literal("Collar Color - " + collarColor)))
                .child(Components.label(Text.literal("Health - " + this.target.getHealth() + "/" + this.target.getMaxHealth()))).padding(Insets.of(10)).verticalAlignment(VerticalAlignment.CENTER).horizontalAlignment(HorizontalAlignment.CENTER)
        ).positioning(Positioning.relative(25, 50)).padding(Insets.of(5)).surface(Surface.DARK_PANEL));
    }
}
