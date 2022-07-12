package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.registry.PeculiarItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {
    @Shadow @Final private static List<BrewingRecipeRegistry.Recipe<Item>> ITEM_RECIPES;

    @Shadow @Final private static List<Ingredient> POTION_TYPES;

    @Shadow @Final private static List<BrewingRecipeRegistry.Recipe<Potion>> POTION_RECIPES;

    @Inject(method = "registerDefaults", at = @At("TAIL"))
    private static void PeculiarPieces$RegisterPotionRecipes(CallbackInfo ci) {
        POTION_TYPES.add(Ingredient.ofItems(PeculiarItems.HIDDEN_POTION));
        ITEM_RECIPES.add(new BrewingRecipeRegistry.Recipe<>(Items.POTION, Ingredient.ofItems(Items.AMETHYST_SHARD), PeculiarItems.HIDDEN_POTION));
        POTION_RECIPES.add(new BrewingRecipeRegistry.Recipe<>(PeculiarPieces.FLIGHT, Ingredient.ofItems(Items.REDSTONE), PeculiarPieces.LONG_FLIGHT));
        POTION_RECIPES.add(new BrewingRecipeRegistry.Recipe<>(Potions.AWKWARD, Ingredient.ofItems(Items.GLOW_BERRIES), PeculiarPieces.GLOWING));
        POTION_RECIPES.add(new BrewingRecipeRegistry.Recipe<>(PeculiarPieces.GLOWING, Ingredient.ofItems(Items.REDSTONE), PeculiarPieces.LONG_GLOWING));
        POTION_RECIPES.add(new BrewingRecipeRegistry.Recipe<>(PeculiarPieces.GLOWING, Ingredient.ofItems(Items.FERMENTED_SPIDER_EYE), PeculiarPieces.CONCEALMENT));
        POTION_RECIPES.add(new BrewingRecipeRegistry.Recipe<>(PeculiarPieces.LONG_GLOWING, Ingredient.ofItems(Items.FERMENTED_SPIDER_EYE), PeculiarPieces.LONG_CONCEALMENT));
        POTION_RECIPES.add(new BrewingRecipeRegistry.Recipe<>(PeculiarPieces.CONCEALMENT, Ingredient.ofItems(Items.REDSTONE), PeculiarPieces.LONG_CONCEALMENT));
    }
}