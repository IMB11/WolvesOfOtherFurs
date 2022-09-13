package mine.block.woof.client.gui;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.BoxComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.*;
import io.wispforest.owo.ui.core.*;
import io.wispforest.owo.ui.event.MouseDown;
import mine.block.woof.server.WoofPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WolfInfoScreen extends BaseOwoScreen<FlowLayout> {
    private final WolfEntity target;

    public WolfInfoScreen(WolfEntity target) {
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

        VerticalFlowLayout container = Containers.verticalFlow(Sizing.content(), Sizing.content());
        DraggableContainer<ParentComponent> Dcontainer = (DraggableContainer<ParentComponent>) Containers.draggable(Sizing.content(), Sizing.content(), container).positioning(Positioning.relative(75, 50)).padding(Insets.of(5)).surface(Surface.DARK_PANEL);

        container.child(Components.label(Text.literal("Personal Information").formatted(Formatting.UNDERLINE)).horizontalTextAlignment(HorizontalAlignment.CENTER).verticalTextAlignment(VerticalAlignment.CENTER).margins(Insets.of(5)));

        if(this.target.hasCustomName()) {
            var tbox = Components.textBox(Sizing.fill(100), this.target.getCustomName().getString());
            tbox.setText(this.target.getCustomName().getString());
            tbox.focusLost().subscribe(() -> {
                NbtCompound compound = new NbtCompound();
                compound.putUuid("id", target.getUuid());
                compound.putString("name", tbox.getText());
                ClientPlayNetworking.send(WoofPackets.UPDATE_DOG_NAME.ID, PacketByteBufs.create().writeNbt(compound));
                MinecraftClient.getInstance().getToastManager().add(new WoofToast("Updated Wolf Name!"));
            });
            container.child(tbox.margins(Insets.of(5)));
        } else {
            var tbox = (TextFieldWidget) Components.textBox(Sizing.fixed(128), "").margins(Insets.of(5));
            tbox.setEditable(false);
            tbox.setTextFieldFocused(true);
            tbox.setText("No nametag.");
            tbox = (TextFieldWidget) tbox.tooltip(Text.literal("No nametag.").formatted(Formatting.RED));
            container.child(tbox);
        }

        DyeColor dyeColor = target.getCollarColor();
        GridLayout layout = Containers.grid(Sizing.content(), Sizing.content(), 2, 1);
        layout.child(Components.box(Sizing.fixed(64), Sizing.fill(16)).color(Color.ofDye(dyeColor)).fill(true).margins(Insets.of(2, 2, 2, 2)), 0, 0);
        LabelComponent label = Components.label(Text.of(dyeColor.getName())).horizontalTextAlignment(HorizontalAlignment.CENTER).verticalTextAlignment(VerticalAlignment.CENTER);
        layout.child(label, 1, 0);
        container.child(layout);

//        HashSet<Pair<DyeItem, LabelComponent>> listElements = new HashSet<>();
//
//        container.child(Components.<Item, GridLayout>list(
//                Registry.ITEM.stream().toList().stream().filter(item -> item instanceof DyeItem).collect(Collectors.toList()),
//                (Consumer<FlowLayout>) flowLayout -> {
//                    flowLayout.padding(Insets.of(5));
//                },
//                item -> {
//                    DyeItem dye = (DyeItem) item;
//
//                    layout.mouseDown().subscribe((mouseX, mouseY, button) -> {
//                        for (Pair<DyeItem, LabelComponent> listElement : listElements) {
//                            listElement.getRight().text(listElement.getLeft().getName());
//                        }
//                        if(button == GLFW.GLFW_MOUSE_BUTTON_1) {
//                            label.text(dye.getName().copy().formatted(Formatting.BOLD, Formatting.UNDERLINE));
//                        }
//                        return true;
//                    });
//
//                    layout.child(label, 2, 1);
//                    listElements.add(new Pair<>(dye, label));
//
//                    return layout;
//                },
//                true
//        ));

        rootComponent.child(Dcontainer);
    }
}
