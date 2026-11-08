package com.illusivesoulworks.veinmining.common.network;

import com.illusivesoulworks.veinmining.common.veinmining.VeinMiningPlayers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

/** 1.20.1 Forge networking: raw FriendlyByteBuf packet (no StreamCodec API). */
public class CPacketState {

  public final boolean activate;

  public CPacketState(boolean activate) {
    this.activate = activate;
  }

  public static void encode(CPacketState packet, FriendlyByteBuf buf) {
    buf.writeBoolean(packet.activate);
  }

  public static CPacketState decode(FriendlyByteBuf buf) {
    return new CPacketState(buf.readBoolean());
  }

  public static void handle(CPacketState packet, ServerPlayer player) {
    if (packet.activate) {
      VeinMiningPlayers.activateVeinMining(player, player.level().getGameTime());
    } else {
      VeinMiningPlayers.deactivateVeinMining(player);
    }
  }
}
