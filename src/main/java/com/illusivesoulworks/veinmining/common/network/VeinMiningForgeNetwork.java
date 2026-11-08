package com.illusivesoulworks.veinmining.common.network;

import com.illusivesoulworks.veinmining.VeinMiningConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/** 1.20.1 Forge SimpleChannel networking (pre-StreamCodec API). */
public class VeinMiningForgeNetwork {

  private static final String PROTOCOL_VERSION = "1";
  private static SimpleChannel instance;
  private static int packetId = 0;

  private static int nextId() {
    return packetId++;
  }

  public static SimpleChannel get() {
    return instance;
  }

  public static void setup() {
    instance = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(VeinMiningConstants.MOD_ID, "main"),
        () -> PROTOCOL_VERSION,
        s -> true,
        s -> true);
    registerState();
    registerNotify();
  }

  private static void registerState() {
    instance.registerMessage(nextId(), CPacketState.class,
        CPacketState::encode, CPacketState::decode,
        (packet, ctx) -> {
          ctx.get().enqueueWork(() -> CPacketState.handle(packet, ctx.get().getSender()));
          ctx.get().setPacketHandled(true);
        });
  }

  private static void registerNotify() {
    instance.registerMessage(nextId(), SPacketNotify.class,
        SPacketNotify::encode, SPacketNotify::decode,
        (packet, ctx) -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
            () -> () -> {
              ctx.get().enqueueWork(SPacketNotify::handle);
              ctx.get().setPacketHandled(true);
            }));
  }
}
