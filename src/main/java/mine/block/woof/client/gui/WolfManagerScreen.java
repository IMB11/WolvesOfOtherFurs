package mine.block.woof.client.gui;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import mine.block.woof.commands.DogCommand;
import mine.block.woof.register.WoofRegistries;
import mine.block.woof.server.WoofPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Objects;

public class WolfManagerScreen extends BaseOwoScreen<FlowLayout> {
    private final WolfEntity target;
    private final FlowLayout leftAnchor = Containers.verticalFlow(Sizing.content(), Sizing.content());
    private final FlowLayout rightAnchor = Containers.verticalFlow(Sizing.content(), Sizing.content());
    private final FlowLayout leftColumn = Containers.verticalFlow(Sizing.fill(100), Sizing.content());
    private final FlowLayout rightColumn = Containers.verticalFlow(Sizing.fill(100), Sizing.content());
    private FlowLayout viewport;

    private int viewportBeginX;
    private int viewportEndX;
    private boolean hasBothColumns = false;

    public WolfManagerScreen(WolfEntity target) {
        this.target = target;
    }

    public static LabelComponent sectionHeader(FlowLayout container, String title, boolean separate) {
        final var label = Components.label(Text.of(title)).shadow(true);
        if (separate) label.margins(Insets.top(20));
        label.margins(label.margins().get().withBottom(5));

        container.child(label);
        return label;
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::horizontalFlow);
    }

    @Override
    protected void init() {
        this.viewportBeginX = (int) ((this.width - this.height) * 0.45);
        this.viewportEndX = (int) (this.width - (this.width - this.height) * 0.45) + 1;

        this.leftAnchor.clearChildren();
        this.rightAnchor.clearChildren();

        this.viewportEndX -= this.viewportBeginX;
        this.viewportBeginX = 0;
        this.hasBothColumns = false;

        this.leftAnchor.horizontalSizing(Sizing.fixed(0)).verticalSizing(Sizing.fixed(this.height));
        this.rightAnchor.positioning(Positioning.absolute(viewportEndX, 0)).horizontalSizing(Sizing.fixed(this.width - this.viewportEndX)).verticalSizing(Sizing.fixed(this.height));

        this.viewport = Containers.verticalFlow(Sizing.fixed(this.viewportEndX), Sizing.content(this.height));

        this.viewport.child(Components.entity(Sizing.fixed(this.height), this.target).scaleToFit(true).allowMouseRotation(true).positioning(Positioning.relative(0, 0)));

        this.rightAnchor.child(
                Containers.verticalScroll(Sizing.fill(100), Sizing.fill(100), Containers.verticalFlow(Sizing.content(), Sizing.content())
                        .child(leftColumn)
                        .child(Components.box(Sizing.fill(85), Sizing.fixed(1)).color(Color.ofDye(DyeColor.GRAY)).fill(true).margins(Insets.top(15)))
                        .child(rightColumn)
                        .horizontalAlignment(HorizontalAlignment.CENTER))

        );

        GLFW.glfwSetWindowAttrib(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

        super.init();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void close() {
        GLFW.glfwSetWindowAttrib(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        super.close();
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        this.leftColumn.margins(Insets.top(20));
        this.rightColumn.margins(Insets.top(20));

        int maxWidth = this.width - this.viewportEndX;

        rootComponent.child(leftAnchor.padding(Insets.left(10)).positioning(Positioning.absolute(0, 0)));
        rootComponent.child(rightAnchor.padding(Insets.left(10)));
        rootComponent.child(viewport);

        sectionHeader(leftColumn, "Info", true);
        sectionHeader(rightColumn, "Tricks", true);

        rightColumn.child(Components.label(Text.literal("Use these tricks to control your dog.")).maxWidth(maxWidth));
        rightColumn.child(
                Containers.verticalScroll(
                        Sizing.content(),
                        Sizing.fill(50),
                        Components.list(
                                new ArrayList<>(WoofRegistries.DOG_COMMAND_REGISTRY.keySet()),
                                flowLayout -> {},
                                (Identifier identifier) -> {
                                    DogCommand command = WoofRegistries.DOG_COMMAND_REGISTRY.get(identifier);
                                    Component buttone = Components.button(command.getText(), button -> {
                                        NbtCompound compound = new NbtCompound();
                                        compound.putUuid("wolfUUID", target.getUuid());
                                        compound.putString("command", identifier.getPath());
                                        command.runClient(compound, this.target);
                                        ClientPlayNetworking.send(WoofPackets.SEND_DOG_COMMAND.ID, PacketByteBufs.create().writeNbt(compound));
                                        this.close();
                                    }).margins(Insets.horizontal(3)).horizontalSizing(Sizing.fill(75));
                                    if(command.getTooltip() != null) {
                                        buttone = buttone.tooltip(command.getTooltip());
                                    }
                                    return buttone;
                                },
                                true)
                ).padding(Insets.of(4)).margins(Insets.left(15))
        );

        String ownerName = Objects.requireNonNull(this.target.getOwner()).getEntityName();
        String collarColor = this.target.getCollarColor().getName();
        collarColor = StringUtils.capitalize(collarColor);

        leftColumn.child(Components.label(Text.literal("Here's some information on your dog!")).maxWidth(maxWidth));

        leftColumn.child(Components.label(Text.literal("Preview")).horizontalTextAlignment(HorizontalAlignment.CENTER))
                .child(Components.label(Text.literal("Statistics").formatted(Formatting.UNDERLINE)).horizontalTextAlignment(HorizontalAlignment.CENTER).margins(Insets.of(5, 2, 0, 0)))
                .child(Components.label(Text.literal("Owner - " + ownerName)))
                .child(Components.label(Text.literal("Collar Color - " + collarColor)))
                .child(Components.label(Text.literal("Health - " + this.target.getHealth() + "/" + this.target.getMaxHealth()))).padding(Insets.of(3)).verticalAlignment(VerticalAlignment.CENTER).horizontalAlignment(HorizontalAlignment.CENTER);

        rootComponent.surface(Surface.VANILLA_TRANSLUCENT);
//        rootComponent.child(Containers.draggable(Sizing.content(), Sizing.content(), Containers.verticalFlow(Sizing.content(), Sizing.content())
//                .child(Components.label(Text.literal("Preview")).horizontalTextAlignment(HorizontalAlignment.CENTER))
//                .child(Components.entity(Sizing.fixed(128), target).scaleToFit(true).allowMouseRotation(true).margins(Insets.of(5)))
//                .child(Components.label(Text.literal("Statistics").formatted(Formatting.UNDERLINE)).horizontalTextAlignment(HorizontalAlignment.CENTER).margins(Insets.of(5, 2, 0, 0)))
//                .child(Components.label(Text.literal("Owner - " + ownerName)))
//                .child(Components.label(Text.literal("Collar Color - " + collarColor)))
//                .child(Components.label(Text.literal("Health - " + this.target.getHealth() + "/" + this.target.getMaxHealth()))).padding(Insets.of(10)).verticalAlignment(VerticalAlignment.CENTER).horizontalAlignment(HorizontalAlignment.CENTER)
//        ).positioning(Positioning.relative(25, 50)).padding(Insets.of(5)).surface(Surface.DARK_PANEL));
    }
}
