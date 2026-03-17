package net.empire.ewmedieval.item;

import net.empire.ewmedieval.util.ModTags;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DogFoodItem extends ConsumableItem {

    // Effects applied to the dog when fed
    public static final List<StatusEffectInstance> EFFECTS = List.of(
            new StatusEffectInstance(StatusEffects.SPEED, 6000, 0),
            new StatusEffectInstance(StatusEffects.STRENGTH, 6000, 0),
            new StatusEffectInstance(StatusEffects.RESISTANCE, 6000, 0)
    );

    public DogFoodItem(Settings settings) {
        super(settings);
    }

    // Call this in your onInitialize() to register the use-on-entity event
    public static void init() {
        UseEntityCallback.EVENT.register(DogFoodItem::onDogFoodApplied);
    }

    private static ActionResult onDogFoodApplied(PlayerEntity player, World world,
                                                 Hand hand, Entity target,
                                                 @Nullable net.minecraft.util.hit.EntityHitResult hitResult) {
        ItemStack stack = player.getStackInHand(hand);

        // Only trigger if the held item is dog food
        if (!(stack.getItem() instanceof DogFoodItem)) {
            return ActionResult.PASS;
        }

        if (target instanceof LivingEntity entity
                && entity.isAlive()
                && target.getType().isIn(ModTags.DOG_FOOD_USERS)) {
            boolean isTameable = entity instanceof TameableEntity;
            // Only feed tamed animals (or non-tameable living entities)
            if (isTameable && !((TameableEntity) entity).isTamed()) {
                return ActionResult.PASS;
            }

            if (!world.isClient) {
                // Restore full health
                entity.setHealth(entity.getMaxHealth());

                // Apply all effects
                for (StatusEffectInstance effect : EFFECTS) {
                    entity.addStatusEffect(new StatusEffectInstance(effect));
                }

                // Play eating sound
                world.playSound(null, target.getBlockPos(),
                        SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 0.8F, 0.8F);

                // Spawn star particles
                Random random = world.getRandom();
                for (int i = 0; i < 5; i++) {
                    double xSpeed = random.nextGaussian() * 0.02D;
                    double ySpeed = random.nextGaussian() * 0.02D;
                    double zSpeed = random.nextGaussian() * 0.02D;
                    // Using heart particles as a substitute since we don't have custom star particles yet
                    world.addParticle(net.minecraft.particle.ParticleTypes.HEART,
                            entity.getParticleX(1.0D),
                            entity.getRandomBodyY() + 0.5D,
                            entity.getParticleZ(1.0D),
                            xSpeed, ySpeed, zSpeed);
                }

                // Return bowl if applicable
                Item remainder = stack.getItem().getRecipeRemainder();
                if (remainder != null && !player.getAbilities().creativeMode) {
                    player.giveItemStack(new ItemStack(remainder));
                    stack.decrement(1);
                }
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world,
                              List<Text> tooltip, net.minecraft.client.item.TooltipContext context) {
        // Show what effects will be applied when feeding
        tooltip.add(Text.translatable("tooltip.ewmedieval.dog_food.when_feeding")
                .formatted(Formatting.GRAY));

        for (StatusEffectInstance effectInstance : EFFECTS) {
            StatusEffect effect = effectInstance.getEffectType();
            Text effectName = effect.getName();

            var line = Text.literal(" ").append(effectName);

            // Add potency if amplifier > 0 (e.g. "Speed II")
            if (effectInstance.getAmplifier() > 0) {
                line = line.copy().append(" ")
                        .append(Text.translatable("potion.potency." + effectInstance.getAmplifier()));
            }

            if (effectInstance.getDuration() > 20) {
                int seconds = effectInstance.getDuration() / 20;
                int minutes = seconds / 60;
                int remainingSeconds = seconds % 60;
                String duration = minutes > 0
                        ? String.format("%d:%02d", minutes, remainingSeconds)
                        : String.format("0:%02d", remainingSeconds);
                line = line.copy().append(" (").append(duration).append(")");
            }

            tooltip.add(line.formatted(effect.getCategory().getFormatting()));
        }
    }
}