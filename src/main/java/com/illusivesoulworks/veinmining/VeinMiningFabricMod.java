package com.illusivesoulworks.veinmining;

import com.illusivesoulworks.veinmining.common.config.VeinMiningConfig;
import com.illusivesoulworks.veinmining.common.data.BlockGroupsResourceListener;
import com.illusivesoulworks.veinmining.common.data.FabricBlockGroupsResourceListener;
import com.illusivesoulworks.veinmining.common.network.CPacketState;
import com.illusivesoulworks.veinmining.common.veinmining.VeinMiningEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;

public class VeinMiningFabricMod implements ModInitializer {

  @Override
  public void onInitialize() {
    VeinMiningConfig.setup();
    // 1.20.1: enchantments are code-registered (datapack enchantments came in 1.20.5)
    Registry.register(Registries.ENCHANTMENT, VeinMiningEnchantment.ID,
        VeinMiningEnchantment.INSTANCE);
    ServerLifecycleEvents.SERVER_STARTED.register(server -> VeinMiningEvents.reloadDatapack());
    ServerTickEvents.END_WORLD_TICK.register(VeinMiningEvents::tick);
    ResourceManagerHelper resourceManagerHelper = ResourceManagerHelper.get(PackType.SERVER_DATA);
    FabricBlockGroupsResourceListener fabricBlockGroupsResourceListener =
        new FabricBlockGroupsResourceListener();
    BlockGroupsResourceListener.INSTANCE = fabricBlockGroupsResourceListener;
    resourceManagerHelper.registerReloadListener(fabricBlockGroupsResourceListener);
    ServerPlayConnectionEvents.DISCONNECT.register(
        (handler, server) -> VeinMiningEvents.playerLoggedOut(handler.getPlayer()));
    PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
      if (player instanceof ServerPlayer serverPlayer) {
        VeinMiningEvents.blockBreak(serverPlayer, pos, state);
      }
    });
    ServerPlayNetworking.registerGlobalReceiver(CPacketState.ID, (server, player, handler, buf, sender) -> {
      boolean activate = buf.readBoolean();
      server.execute(() -> CPacketState.handle(activate, player));
    });
    ServerEntityEvents.EQUIPMENT_CHANGE.register(
        (livingEntity, equipmentSlot, previousStack, currentStack) -> VeinMiningEvents.toolEquip(
            currentStack, previousStack, equipmentSlot, livingEntity));
  }
}
