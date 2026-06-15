package com.casinomod.event;

import com.casinomod.CasinoMod;
import com.casinomod.ModItems;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = CasinoMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        Level level = player.level();
        if (level.isClientSide()) {
            return;
        }

        // Count all casino coins in the player's inventory
        Inventory inventory = player.getInventory();
        int totalCoins = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack slotStack = inventory.getItem(i);
            if (slotStack.is(ModItems.CASINO_COIN.get())) {
                totalCoins += slotStack.getCount();
            }
        }

        if (totalCoins > 0) {
            // Explosion strength: base 4.0 (TNT) + 1.0 per coin, no cap
            float explosionStrength = 4.0f + (totalCoins * 1.0f);

            CasinoMod.LOGGER.info("Player {} died with {} casino coins! Explosion strength: {}",
                    player.getName().getString(), totalCoins, explosionStrength);

            level.explode(player,
                    player.getX(), player.getY(), player.getZ(),
                    explosionStrength,
                    Level.ExplosionInteraction.TNT);
        }
    }
}
