package com.casinomod;

import com.casinomod.loot.ModLootModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CasinoMod.MOD_ID)
public class CasinoMod {
    public static final String MOD_ID = "casinomod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public CasinoMod(IEventBus modEventBus) {
        LOGGER.info("CasinoMod loading...");

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        LOGGER.info("CasinoMod loaded!");
    }
}
