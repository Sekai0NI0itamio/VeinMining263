package com.illusivesoulworks.veinmining.client;

import com.illusivesoulworks.veinmining.common.network.SPacketNotify;
import com.illusivesoulworks.veinmining.common.veinmining.VeinMiningKey;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class VeinMiningFabricClientMod implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    VeinMiningKey.setup();
    KeyBindingHelper.registerKeyBinding(VeinMiningKey.get());
    ClientTickEvents.END_CLIENT_TICK.register(client -> VeinMiningClientEvents.tick());
    ItemTooltipCallback.EVENT.register(
        (stack, context, lines) -> VeinMiningClientEvents.tooltip(stack, lines));
    ClientPlayNetworking.registerGlobalReceiver(SPacketNotify.ID,
        (client, handler, buf, sender) -> client.execute(SPacketNotify::handle));
  }
}
