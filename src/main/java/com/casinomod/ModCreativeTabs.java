package com.casinomod;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CasinoMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CASINO_TAB =
            CREATIVE_MODE_TABS.register("casino_tab", () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.CASINO_COIN.get().getDefaultInstance())
                    .title(Component.translatable("itemGroup." + CasinoMod.MOD_ID + ".casino_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.CASINO_COIN.get());
                        output.accept(ModBlocks.CASINO_MACHINE_ITEM.get());
                    })
                    .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
