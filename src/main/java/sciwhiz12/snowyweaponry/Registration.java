package sciwhiz12.snowyweaponry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import sciwhiz12.snowyweaponry.entity.CoredSnowballEntity;
import sciwhiz12.snowyweaponry.item.CoredSnowballItem;

import static sciwhiz12.snowyweaponry.Reference.ITEM_GROUP;
import static sciwhiz12.snowyweaponry.SnowyWeaponry.COMMON;
import static sciwhiz12.snowyweaponry.SnowyWeaponry.LOG;

/**
 * Main class for registering objects of this mod.
 *
 * @author SciWhiz12
 */
@EventBusSubscriber(modid = SnowyWeaponry.MODID, bus = Bus.MOD)
public final class Registration {
    private Registration() {} // Prevent instantiation

    @SubscribeEvent
    static void onRegisterItems(RegistryEvent.Register<Item> event) {
        LOG.debug(COMMON, "Registering items");
        event.getRegistry().registerAll(
            new Item(itemProps().maxStackSize(64).group(ItemGroup.MISC)).setRegistryName("diamond_chunk"),
            new Item(itemProps().maxStackSize(64).group(ItemGroup.MISC)).setRegistryName("netherite_nugget"),

            new CoredSnowballItem(itemProps().maxStackSize(16).group(ITEM_GROUP), 2, 0,
                null).setRegistryName("iron_cored_snowball"),
            new CoredSnowballItem(itemProps().maxStackSize(16).group(ITEM_GROUP), 1, 1,
                null).setRegistryName("gold_cored_snowball"),
            new CoredSnowballItem(itemProps().maxStackSize(16).group(ITEM_GROUP), 3, 0,
                () -> new EffectInstance(Effects.SLOWNESS, 30, 0, false, true, false))
                .setRegistryName("diamond_cored_snowball"),
            new CoredSnowballItem(itemProps().maxStackSize(16).group(ITEM_GROUP), 4, 0,
                () -> new EffectInstance(Effects.BLINDNESS, 40, 0, false, true, false))
                .setRegistryName("netherite_cored_snowball")
        );
    }

    @SubscribeEvent
    static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
        LOG.debug(COMMON, "Registering entity types");
        event.getRegistry().registerAll(
            build(EntityType.Builder.<CoredSnowballEntity>create(CoredSnowballEntity::new, EntityClassification.MISC)
                    .size(0.25F, 0.25F).trackingRange(4).func_233608_b_(10),
                "cored_snowball")
        );
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Entity> EntityType<T> build(EntityType.Builder<T> builder, String entityName) {
        ResourceLocation name = new ResourceLocation(SnowyWeaponry.MODID, entityName);
        EntityType<T> type = builder.build(name.toString());
        type.setRegistryName(name);
        return type;
    }

    private static Item.Properties itemProps() { return new Item.Properties(); }
}
