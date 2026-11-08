package com.illusivesoulworks.veinmining.common.network;

import com.illusivesoulworks.veinmining.VeinMiningConstants;
import com.illusivesoulworks.veinmining.common.veinmining.VeinMiningPlayers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/** 1.20.1 networking: raw FriendlyByteBuf packets (no StreamCodec API). */
public class CPacketState {

  public static final ResourceLocation ID =
      new ResourceLocation(VeinMiningConstants.MOD_ID, "state");

  public static void handle(boolean activate, ServerPlayer player) {
    if (activate) {
      VeinMiningPlayers.activateVeinMining(player, player.level().getGameTime());
    } else {
      VeinMiningPlayers.deactivateVeinMining(player);
    }
  }
}
