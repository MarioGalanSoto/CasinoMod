package com.casinomod;

import com.casinomod.block.CasinoMachineBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CasinoMod.MOD_ID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(CasinoMod.MOD_ID);

    public static final DeferredBlock<Block> CASINO_MACHINE = BLOCKS.register("casino_machine",
            () -> new CasinoMachineBlock(BlockBehaviour.Properties.of()
                    .strength(2.0f, 6.0f)
                    .sound(SoundType.GRAVEL)
                    .requiresCorrectToolForDrops()));

    public static final DeferredItem<BlockItem> CASINO_MACHINE_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "casino_machine", CASINO_MACHINE);

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        BLOCK_ITEMS.register(modEventBus);
    }
}
