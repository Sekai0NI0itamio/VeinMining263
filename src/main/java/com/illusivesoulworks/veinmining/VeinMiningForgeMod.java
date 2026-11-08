package com.illusivesoulworks.veinmining;

import com.illusivesoulworks.veinmining.client.ForgeClientEventsListener;
import com.illusivesoulworks.veinmining.common.ForgeCommonEventsListener;
import com.illusivesoulworks.veinmining.common.config.VeinMiningConfig;
import com.illusivesoulworks.veinmining.common.network.VeinMiningForgeNetwork;
import com.illusivesoulworks.veinmining.common.veinmining.VeinMiningKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(VeinMiningConstants.MOD_ID)
public class VeinMiningForgeMod {

  // 1.20.1: enchantments are code-registered (datapack enchantments came in 1.20.5).
  // The DeferredRegister supplier returns the shared INSTANCE so that
  // EnchantmentHelper lookups against VeinMiningEnchantment.INSTANCE work.
  private static final DeferredRegister<Enchantment> ENCHANTMENTS =
      DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, VeinMiningConstants.MOD_ID);
  public static final RegistryObject<Enchantment> VEIN_MINING =
      ENCHANTMENTS.register("vein_mining", () -> VeinMiningEnchantment.INSTANCE);

  public VeinMiningForgeMod() {
    VeinMiningConfig.setup();
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    ENCHANTMENTS.register(eventBus);
    eventBus.addListener(this::setup);
    eventBus.addListener(this::clientSetup);
  }

  private void setup(final FMLCommonSetupEvent evt) {
    VeinMiningForgeNetwork.setup();
    MinecraftForge.EVENT_BUS.register(new ForgeCommonEventsListener());
  }

  private void clientSetup(final net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent evt) {
    MinecraftForge.EVENT_BUS.register(new ForgeClientEventsListener());
  }

  @Mod.EventBusSubscriber(modid = VeinMiningConstants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModEvents {

    @SubscribeEvent
    public static void registerKeys(final RegisterKeyMappingsEvent evt) {
      VeinMiningKey.setup();
      evt.register(VeinMiningKey.get());
    }
  }
}
