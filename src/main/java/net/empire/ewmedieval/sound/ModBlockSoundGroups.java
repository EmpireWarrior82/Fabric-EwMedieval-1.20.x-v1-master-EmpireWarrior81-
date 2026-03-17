package net.empire.ewmedieval.sound;

import net.minecraft.sound.BlockSoundGroup;

public class ModBlockSoundGroups {

    public static final BlockSoundGroup POLISHED_TUFF = new BlockSoundGroup(
            1.0f, 1.0f,
            ModSounds.POLISHED_TUFF_BREAK,
            ModSounds.POLISHED_TUFF_STEP,
            ModSounds.POLISHED_TUFF_PLACE,
            ModSounds.POLISHED_TUFF_HIT,
            ModSounds.POLISHED_TUFF_FALL
    );

    public static final BlockSoundGroup TUFF_BRICKS = new BlockSoundGroup(
            1.0f, 1.0f,
            ModSounds.TUFF_BRICKS_BREAK,
            ModSounds.TUFF_BRICKS_STEP,
            ModSounds.TUFF_BRICKS_PLACE,
            ModSounds.TUFF_BRICKS_HIT,
            ModSounds.TUFF_BRICKS_FALL
    );
}
