package com.casinomod.loot;

import com.casinomod.ModItems;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class AddCoinDropsModifier extends LootModifier {

    public static final MapCodec<AddCoinDropsModifier> CODEC =
            RecordCodecBuilder.mapCodec(inst -> codecStart(inst)
                    .apply(inst, AddCoinDropsModifier::new));

    public AddCoinDropsModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        // Add 3 casino coins to the drops
        generatedLoot.add(new ItemStack(ModItems.CASINO_COIN.get(), 3));
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
