/*
 * Copyright (C) 2022 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.client.gui;

import io.wispforest.owo.ui.util.UIErrorToast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WoofToast extends UIErrorToast {
    public WoofToast(String message) {
        super(message);
    }

    @Override
    public Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        return super.draw(matrices, manager, startTime);
    }

    private List<Text> initText(String errorMessage, Consumer<Consumer<Text>> contextAppender) {
        final var texts = new ArrayList<Text>();

        texts.add(Text.literal(errorMessage));

        return texts;
    }
}
