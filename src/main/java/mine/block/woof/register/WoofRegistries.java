package mine.block.woof.register;

import mine.block.woof.commands.DogCommand;
import mine.block.woof.register.block.WoofBlocks;
import mine.block.woof.register.item.WoofItems;
import net.minecraft.util.Identifier;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;

public class WoofRegistries {
    public static HashMap<Identifier, DogCommand> DOG_COMMAND_REGISTRY = new HashMap<>();

    public static void initialize() {
        WoofBlocks.init();
        WoofItems.init();

        try {
            Reflections ref = new Reflections("mine.block.woof.register");
            Set<Class<? extends DogCommand>> subTypes = ref.getSubTypesOf(DogCommand.class);
            for (Class<? extends DogCommand> subType : subTypes) {
                DogCommand command = subType.getConstructor().newInstance();
                DOG_COMMAND_REGISTRY.put(command.getID(), command);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
