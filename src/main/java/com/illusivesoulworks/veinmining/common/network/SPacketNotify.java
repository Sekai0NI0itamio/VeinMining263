package com.illusivesoulworks.veinmining.common.network;

import net.minecraft.network.FriendlyByteBuf;

/** 1.20.1 Forge networking: raw FriendlyByteBuf packet (no StreamCodec API). */
public class SPacketNotify {

  public static final SPacketNotify INSTANCE = new SPacketNotify();

  public static void encode(SPacketNotify packet, FriendlyByteBuf buf) {
  }

  public static SPacketNotify decode(FriendlyByteBuf buf) {
    return INSTANCE;
  }

  public static void handle() {
    ClientPackets.handleNotification();
  }
}
