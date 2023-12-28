package xyz.aikoyori.petbottlerocket;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.client.gl.Uniform;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.damage.DamageEffects;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DeathMessageType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.aikoyori.petbottlerocket.entity.WaterRocketEntity;
import xyz.aikoyori.petbottlerocket.item.WaterBottleItem;
import xyz.aikoyori.petbottlerocket.item.WaterRocketItem;
import xyz.aikoyori.petbottlerocket.utils.ModUtils;

public class PetbottleRocket implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    public static final EntityType<WaterRocketEntity> WATER_ROCKET_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            ModUtils.makeID("water_rocket"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC,WaterRocketEntity::new).dimensions(EntityDimensions.fixed(4/16f,4/16f)).build()
            );
    public static final WaterRocketItem WATER_ROCKET_ITEM = new WaterRocketItem(new FabricItemSettings().maxCount(1));
    public static final Item BOTTLE_CAP_ITEM = new Item(new FabricItemSettings());
    public static final Item WATER_BOTTLE_ITEM = new Item(new FabricItemSettings());
    public static final Item CAPLESS_WATER_BOTTLE_ITEM = new Item(new FabricItemSettings());
    public static final Item BOTTLE_ITEM = new WaterBottleItem(new FabricItemSettings(),WATER_BOTTLE_ITEM);
    public static final Item CAPLESS_BOTTLE_ITEM = new WaterBottleItem(new FabricItemSettings(),CAPLESS_WATER_BOTTLE_ITEM);
    public static final Item PLASTIC_SCRAP_ITEM = new Item(new FabricItemSettings());
    public static final Item FIN_ITEM = new Item(new FabricItemSettings());
    public static final Item ROCKET_CAP_ITEM = new Item(new FabricItemSettings());
    public static final RegistryKey<DamageType> ROCKET_HIT_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, ModUtils.makeID("rocket_hit"));

    public static final ItemGroup PET_BOTTLE_MOD_GROUP = FabricItemGroup.builder()
            .icon(()->new ItemStack(WATER_ROCKET_ITEM))
            .displayName(Text.translatable("itemGroup.petbottlerocket.water_rocket"))
            .entries((displayContext, entries) -> {
                entries.add(WATER_ROCKET_ITEM);
                entries.add(WATER_BOTTLE_ITEM);
                entries.add(CAPLESS_WATER_BOTTLE_ITEM);
                entries.add(BOTTLE_ITEM);
                entries.add(CAPLESS_BOTTLE_ITEM);
                entries.add(PLASTIC_SCRAP_ITEM);
                entries.add(BOTTLE_CAP_ITEM);
                entries.add(FIN_ITEM);
                entries.add(ROCKET_CAP_ITEM);
            })
            .build();
    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, ModUtils.makeID("water_rocket"),WATER_ROCKET_ITEM);
        Registry.register(Registries.ITEM, ModUtils.makeID("bottle"),BOTTLE_ITEM);
        Registry.register(Registries.ITEM, ModUtils.makeID("bottle_cap"),BOTTLE_CAP_ITEM);
        Registry.register(Registries.ITEM, ModUtils.makeID("capless_bottle"),CAPLESS_BOTTLE_ITEM);
        Registry.register(Registries.ITEM, ModUtils.makeID("plastic_scrap"),PLASTIC_SCRAP_ITEM);
        Registry.register(Registries.ITEM, ModUtils.makeID("fin"),FIN_ITEM);
        Registry.register(Registries.ITEM, ModUtils.makeID("rocket_cap"),ROCKET_CAP_ITEM);
        Registry.register(Registries.ITEM, ModUtils.makeID("water_bottle"),WATER_BOTTLE_ITEM);
        Registry.register(Registries.ITEM, ModUtils.makeID("capless_water_bottle"),CAPLESS_WATER_BOTTLE_ITEM);
        Registry.register(Registries.ITEM_GROUP, ModUtils.makeID("water_rocket"),PET_BOTTLE_MOD_GROUP);
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            // Let's only modify built-in loot tables and leave data pack loot tables untouched by checking the source.
            // We also check that the loot table ID is equal to the ID we want.
            if (source.isBuiltin()) {
                LootPool.Builder poolBuilder = LootPool.builder();
                if(LootTables.VILLAGE_WEAPONSMITH_CHEST.equals(id))
                {

                    poolBuilder.with(ItemEntry.builder(PLASTIC_SCRAP_ITEM).weight(2))
                            .rolls(UniformLootNumberProvider.create(2,3)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4,7)));
                }
                if(LootTables.SIMPLE_DUNGEON_CHEST.equals(id))
                {

                    poolBuilder.with(ItemEntry.builder(BOTTLE_CAP_ITEM).weight(1))
                            .rolls(UniformLootNumberProvider.create(1,1)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,3)));
                }



                tableBuilder.pool(poolBuilder);
            }
        });

    }
}
