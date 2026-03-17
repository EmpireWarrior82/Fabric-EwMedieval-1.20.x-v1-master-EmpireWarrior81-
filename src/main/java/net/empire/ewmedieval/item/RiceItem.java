package net.empire.ewmedieval.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;

// RiceItem is a seed-like item that places a rice crop block when used on farmland or dirt.
// If placement fails and the player clicked the top of a dirt/farmland block, show a hint message.
public class RiceItem extends AliasedBlockItem {

    public RiceItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // Try to place the rice crop block normally
        ActionResult result = this.place(new ItemPlacementContext(context));

        if (result == ActionResult.FAIL) {
            // Placement failed — check if the player clicked the top of dirt or farmland
            // and show a helpful hint explaining why it can't be placed
            var player = context.getPlayer();
            BlockState targetState = context.getWorld().getBlockState(context.getBlockPos());
            boolean clickedTop = context.getSide() == Direction.UP;
            boolean validGround = targetState.isIn(BlockTags.DIRT)
                    || targetState.getBlock() instanceof FarmlandBlock;

            if (player != null && clickedTop && validGround) {
                player.sendMessage(
                        Text.translatable("block.ewmedieval.rice.invalid_placement"),
                        true // action bar message, not chat
                );
            }
        }

        // If placement didn't consume the action and the item is edible, try eating it instead
        if (!result.isAccepted() && this.isFood()) {
            return this.use(context.getWorld(), context.getPlayer(), context.getHand()).getResult();
        }

        return result;
    }
}