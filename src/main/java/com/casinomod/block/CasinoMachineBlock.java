package com.casinomod.block;

import com.casinomod.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public class CasinoMachineBlock extends Block {

    public CasinoMachineBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level,
                                               BlockPos pos, Player player, InteractionHand hand,
                                               BlockHitResult hitResult) {
        // Only accept casino coins
        if (!stack.is(ModItems.CASINO_COIN.get())) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!level.isClientSide()) {
            // Consume one coin
            stack.shrink(1);

            // Roll the dice
            float roll = level.getRandom().nextFloat() * 100f;

            if (roll < 45f) {
                // 45% chance: 1 to 5 random items
                List<Item> allItems = new ArrayList<>(BuiltInRegistries.ITEM.stream().toList());
                Item randomItem = allItems.get(level.getRandom().nextInt(allItems.size()));
                int quantity = 1 + level.getRandom().nextInt(5);

                spawnItem(level, pos, new ItemStack(randomItem, quantity));
                player.displayClientMessage(Component.literal("§a§lJACKPOT! §rYou won " + quantity + "x " + new ItemStack(randomItem).getHoverName().getString() + "!"), true);
                level.playSound(null, pos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else if (roll < 65f) {
                // 20% chance: 5 to 100 monedas
                int quantity = 5 + level.getRandom().nextInt(96);
                int remaining = quantity;
                while (remaining > 0) {
                    int toSpawn = Math.min(remaining, 64);
                    spawnItem(level, pos, new ItemStack(ModItems.CASINO_COIN.get(), toSpawn));
                    remaining -= toSpawn;
                }
                player.displayClientMessage(Component.literal("§e§lCOINS! §rYou won " + quantity + "x Casino Coins!"), true);
                level.playSound(null, pos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else if (roll < 80f) {
                // 15% chance: nothing
                player.displayClientMessage(Component.literal("§7Better luck next time..."), true);
                level.playSound(null, pos, SoundEvents.DISPENSER_FAIL, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else if (roll < 86f) {
                // 6% chance: instant explosion 6
                player.displayClientMessage(Component.literal("§c§lBOOM! The machine explodes!"), true);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 6.0f, Level.ExplosionInteraction.TNT);
            } else if (roll < 91f) {
                // 5% chance: 256 wooden shovels
                for (int i = 0; i < 256; i++) {
                    spawnItem(level, pos, new ItemStack(net.minecraft.world.item.Items.WOODEN_SHOVEL));
                }
                player.displayClientMessage(Component.literal("§b§lPROMO! §rYou won 256 Wooden Shovels!"), true);
                level.playSound(null, pos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else if (roll < 95f) {
                // 4% chance: lightning strike
                player.displayClientMessage(Component.literal("§e§lZAP! Lightning strikes!"), true);
                net.minecraft.world.entity.LightningBolt lightning = net.minecraft.world.entity.EntityType.LIGHTNING_BOLT.create(level);
                if (lightning != null) {
                    lightning.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    level.addFreshEntity(lightning);
                }
            } else if (roll < 98f) {
                // 3% chance: zumbon de 22 bloques
                player.displayClientMessage(Component.literal("§c§lZUMBON! Massive explosion!"), true);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 22.0f, Level.ExplosionInteraction.TNT);
            } else if (roll < 99.5f) {
                // 1.5% chance: TNT explosion of 15
                player.displayClientMessage(Component.literal("§4§lER DIABLO! TNT explosion!"), true);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 15.0f, Level.ExplosionInteraction.TNT);
            } else {
                // 0.5% chance: Wither
                player.displayClientMessage(Component.literal("§8§lOH NO! The Wither has been unleashed!"), true);
                net.minecraft.world.entity.boss.wither.WitherBoss wither = net.minecraft.world.entity.EntityType.WITHER.create(level);
                if (wither != null) {
                    wither.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                    wither.makeInvulnerable();
                    level.addFreshEntity(wither);
                }
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    private void spawnItem(Level level, BlockPos pos, ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(level,
                pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                stack);
        itemEntity.setDeltaMovement(
                (level.getRandom().nextFloat() - 0.5) * 0.2,
                0.3,
                (level.getRandom().nextFloat() - 0.5) * 0.2);
        level.addFreshEntity(itemEntity);
    }
}
