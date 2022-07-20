package amymialee.peculiarpieces;

import amymialee.peculiarpieces.callbacks.PlayerCrouchCallback;
import amymialee.peculiarpieces.callbacks.PlayerCrouchConsumingBlock;
import amymialee.peculiarpieces.callbacks.PlayerJumpCallback;
import amymialee.peculiarpieces.callbacks.PlayerJumpConsumingBlock;
import amymialee.peculiarpieces.effects.FlightStatusEffect;
import amymialee.peculiarpieces.effects.OpenStatusEffect;
import amymialee.peculiarpieces.registry.PeculiarBlocks;
import amymialee.peculiarpieces.registry.PeculiarEntities;
import amymialee.peculiarpieces.registry.PeculiarItems;
import amymialee.peculiarpieces.screens.PackedPouchScreenHandler;
import amymialee.peculiarpieces.screens.PedestalScreenHandler;
import amymialee.peculiarpieces.screens.PotionPadScreenHandler;
import amymialee.peculiarpieces.screens.WarpScreenHandler;
import amymialee.peculiarpieces.util.ExtraPlayerDataWrapper;
import amymialee.peculiarpieces.util.WarpManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Potion;
import net.minecraft.screen.ScreenHandlerType;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;

import java.util.Collection;
import java.util.Random;

@SuppressWarnings("unused")
public class PeculiarPieces implements ModInitializer {
    public static final String MOD_ID = "peculiarpieces";
    public static final Random RANDOM = new Random();
    //ItemGroups
    public static final ItemGroup PIECES_GROUP = FabricItemGroupBuilder.create(id("peculiarpieces_group")).icon(PeculiarItems::getPeculiarIcon).build();
    public static final ItemGroup CREATIVE_GROUP = FabricItemGroupBuilder.create(id("peculiarpieces_creative_group")).icon(PeculiarItems::getCreativeIcon).build();
    public static final ItemGroup POTION_GROUP = FabricItemGroupBuilder.create(id("peculiarpieces_potion_group")).icon(PeculiarItems::getPotionIcon).build();
    //ScreenHandlers
    public static final ScreenHandlerType<WarpScreenHandler> WARP_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, "warp_block", new ScreenHandlerType<>(WarpScreenHandler::new));
    public static final ScreenHandlerType<PotionPadScreenHandler> POTION_PAD_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, "potion_pad", new ScreenHandlerType<>(PotionPadScreenHandler::new));
    public static final ScreenHandlerType<PackedPouchScreenHandler> BUSTLING_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, "bustling_bundle", new ScreenHandlerType<>((a, b) -> new PackedPouchScreenHandler(a, b, PeculiarItems.PACKED_POUCH.getDefaultStack().copy())));
    public static final ScreenHandlerType<PedestalScreenHandler> PEDESTAL_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, "pedestal", new ScreenHandlerType<>(PedestalScreenHandler::new));
    //Tags
    public static final TagKey<EntityType<?>> MOUNT_BLACKLIST = TagKey.of(Registry.ENTITY_TYPE_KEY, id("mount_blacklist"));
    public static final TagKey<EntityType<?>> UNGRABBABLE = TagKey.of(Registry.ENTITY_TYPE_KEY, id("ungrabbable"));
    public static final TagKey<Block> WARP_BINDABLE = TagKey.of(Registry.BLOCK_KEY, id("warp_bindable"));
    public static final TagKey<Block> SCAFFOLDING = TagKey.of(Registry.BLOCK_KEY, id("scaffolding"));
    public static final TagKey<Block> SHEARS_MINEABLE = TagKey.of(Registry.BLOCK_KEY, id("mineable/shears"));
    public static final TagKey<Item> BARRIERS = TagKey.of(Registry.ITEM_KEY, id("barriers"));
    //Flight
    public static final StatusEffect FLIGHT_EFFECT = Registry.register(Registry.STATUS_EFFECT, id("flight"), new FlightStatusEffect(StatusEffectCategory.BENEFICIAL, 6670591));
    public static final Potion FLIGHT = Registry.register(Registry.POTION, id("flight"), new Potion(new StatusEffectInstance(FLIGHT_EFFECT, 3600)));
    public static final Potion LONG_FLIGHT = Registry.register(Registry.POTION, id("long_flight"), new Potion("flight", new StatusEffectInstance(FLIGHT_EFFECT, 9600)));
    //Glowing
    public static final Potion GLOWING = Registry.register(Registry.POTION, id("glowing"), new Potion(new StatusEffectInstance(StatusEffects.GLOWING, 3600)));
    public static final Potion LONG_GLOWING = Registry.register(Registry.POTION, id("long_glowing"), new Potion("glowing", new StatusEffectInstance(StatusEffects.GLOWING, 9600)));
    //Concealment
    public static final StatusEffect CONCEALMENT_EFFECT = Registry.register(Registry.STATUS_EFFECT, id("concealment"), new OpenStatusEffect(StatusEffectCategory.BENEFICIAL, 8356754));
    public static final Potion CONCEALMENT = Registry.register(Registry.POTION, id("concealment"), new Potion(new StatusEffectInstance(CONCEALMENT_EFFECT, 3600)));
    public static final Potion LONG_CONCEALMENT = Registry.register(Registry.POTION, id("long_concealment"), new Potion("concealment", new StatusEffectInstance(CONCEALMENT_EFFECT, 9600)));
    //Gamerules
    public static final GameRules.Key<GameRules.BooleanRule> DO_EXPLOSIONS_BREAK = GameRuleRegistry.register("pp:explosionsBreakBlocks", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> DO_EXPLOSIONS_ALWAYS_DROP = GameRuleRegistry.register("pp:explosionsAlwaysDrop", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> NO_MOB_PUSHING = GameRuleRegistry.register("pp:doEntityPush", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
    //SoundEvents
    public static final SoundEvent ENTITY_SHEEP_YIPPEE = Registry.register(Registry.SOUND_EVENT, "peculiarpieces.sheep.yippee", new SoundEvent(id("peculiarpieces.sheep.yippee")));
    public static final SoundEvent ENTITY_SHEEP_YIPPEE_ENGINEER = Registry.register(Registry.SOUND_EVENT, "peculiarpieces.sheep.yippee_engineer", new SoundEvent(id("peculiarpieces.sheep.yippee_engineer")));

    @Override
    public void onInitialize() {
        PeculiarItems.init();
        PeculiarBlocks.init();
        PeculiarEntities.init();
        CommandRegistrationCallback.EVENT.register((dispatcher, access, environment) -> dispatcher.register(
                literal("peculiar")
                        .then(literal("checkpoint"))
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(argument("targets", EntityArgumentType.players())
                                .then(argument("location", Vec3ArgumentType.vec3())
                                        .executes(ctx -> {
                                            Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(ctx, "targets");
                                            Vec3d pos = Vec3ArgumentType.getPosArgument(ctx, "location").toAbsolutePos(ctx.getSource());
                                            for (ServerPlayerEntity target : targets) {
                                                ((ExtraPlayerDataWrapper) target).setCheckpointPos(pos);
                                            }
                                            if (targets.size() == 1) {
                                                ctx.getSource().sendFeedback(Text.translatable("commands.checkpoint.success.single", pos.getX(), pos.getY(), pos.getZ(), targets.iterator().next().getDisplayName()), true);
                                            } else {
                                                ctx.getSource().sendFeedback(Text.translatable("commands.checkpoint.success.multiple", pos.getX(), pos.getY(), pos.getZ(), targets.size()), true);
                                            }
                                            return 0;
                                        })))));
        ServerTickEvents.END_WORLD_TICK.register(serverWorld -> WarpManager.tick());
        PlayerCrouchCallback.EVENT.register((player, world) -> {
            BlockPos pos = player.getBlockPos().add(0, -1, 0);
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof PlayerCrouchConsumingBlock block) {
                block.onCrouch(state, world, pos, player);
            }
        });
        PlayerJumpCallback.EVENT.register((player, world) -> {
            for (int i = -1; i <= 0; i++) {
                BlockPos pos = player.getBlockPos().add(0, i, 0);
                BlockState state = world.getBlockState(pos);
                if (state.getBlock() instanceof PlayerJumpConsumingBlock block) {
                    block.onJump(state, world, pos, player);
                }
            }
        });
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}