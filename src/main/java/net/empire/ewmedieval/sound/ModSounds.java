package net.empire.ewmedieval.sound;

import net.empire.ewmedieval.EwMedieval;
import net.empire.ewmedieval.util.LoggerUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent POLISHED_TUFF_BREAK = register("block.polished_tuff.break");
    public static final SoundEvent POLISHED_TUFF_PLACE = register("block.polished_tuff.place");
    public static final SoundEvent POLISHED_TUFF_STEP  = register("block.polished_tuff.step");
    public static final SoundEvent POLISHED_TUFF_HIT   = register("block.polished_tuff.hit");
    public static final SoundEvent POLISHED_TUFF_FALL  = register("block.polished_tuff.fall");

    public static final SoundEvent TUFF_BRICKS_BREAK = register("block.tuff_bricks.break");
    public static final SoundEvent TUFF_BRICKS_PLACE = register("block.tuff_bricks.place");
    public static final SoundEvent TUFF_BRICKS_STEP  = register("block.tuff_bricks.step");
    public static final SoundEvent TUFF_BRICKS_HIT   = register("block.tuff_bricks.hit");
    public static final SoundEvent TUFF_BRICKS_FALL  = register("block.tuff_bricks.fall");

    public static final SoundEvent BLOCK_CUTTING_BOARD_KNIFE = register("block.cutting_board.knife");

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(EwMedieval.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds() {
        LoggerUtil.logDebug("Registering mod sounds for " + EwMedieval.MOD_ID);
    }
}
