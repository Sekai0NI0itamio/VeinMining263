package com.illusivesoulworks.veinmining.common.network;

import com.illusivesoulworks.veinmining.VeinMiningConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/** 1.20.1 networking: raw FriendlyByteBuf packets (no StreamCodec API). */
public class SPacketNotify {

  public static final ResourceLocation ID =
      new ResourceLocation(VeinMiningConstants.MOD_ID, "notify");

  public static void handle() {
    ClientPackets.handleNotification();
  }

  public static FriendlyByteBuf encode(FriendlyByteBuf buf) {
    return buf;
  }
}
