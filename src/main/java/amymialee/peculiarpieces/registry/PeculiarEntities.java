package amymialee.peculiarpieces.registry;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.entity.TeleportItemEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class PeculiarEntities {
    public static final EntityType<TeleportItemEntity> TELEPORT_ITEM_ENTITY = registerItem("teleport_item_entity", FabricEntityTypeBuilder.<TeleportItemEntity>create(SpawnGroup.MISC, TeleportItemEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(6).trackedUpdateRate(20).build());

    public static void init() {}

    public static <T extends Entity> EntityType<T> registerItem(String name, EntityType<T> entity) {
        Registry.register(Registry.ENTITY_TYPE, PeculiarPieces.id(name), entity);
        return entity;
    }
}