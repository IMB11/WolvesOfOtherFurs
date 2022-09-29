package mine.block.woof.register;

import mine.block.woof.commands.DogCommand;
import mine.block.woof.commands.actions.BarkCommand;
import mine.block.woof.commands.actions.GoAwayCommand;
import mine.block.woof.commands.actions.HowlCommand;
import mine.block.woof.commands.actions.JumpCommand;
import mine.block.woof.commands.generic.PetCommand;
import mine.block.woof.register.block.WoofBlocks;
import mine.block.woof.register.item.WoofItems;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;

public class WoofRegistries {
    public static HashMap<Identifier, DogCommand> DOG_COMMAND_REGISTRY = new HashMap<>();

    public static void initialize() {
        WoofBlocks.init();
        WoofItems.init();

        DogCommand[] dogCommands = new DogCommand[] {new GoAwayCommand(), new PetCommand(), new BarkCommand(), new HowlCommand(), new JumpCommand()};

        for (DogCommand dogCommand : dogCommands) {
            DOG_COMMAND_REGISTRY.put(dogCommand.getID(), dogCommand);
        }
    }
}
