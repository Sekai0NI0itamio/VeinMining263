package com.illusivesoulworks.veinmining.platform;

import com.illusivesoulworks.veinmining.common.network.CPacketState;
import com.illusivesoulworks.veinmining.common.platform.services.IClientPlatform;
import com.illusivesoulworks.veinmining.common.veinmining.VeinMiningKey;
import com.mojang.blaze3d.platform.InputConstants;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.FriendlyByteBuf;

public class FabricClientPlatform implements IClientPlatform {

  @Override
  public KeyMapping createKeyMapping(int key, String desc, String category) {
    return new VeinMiningKey.Mapping("key.veinmining.activate.desc",
        InputConstants.UNKNOWN.getValue(),
        "key.veinmining.category");
  }

  @Override
  public void sendC2SState(boolean enabled) {
    FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
    buf.writeBoolean(enabled);
    ClientPlayNetworking.send(CPacketState.ID, buf);
  }
}
