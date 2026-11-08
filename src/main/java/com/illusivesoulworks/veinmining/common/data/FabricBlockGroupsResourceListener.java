package com.illusivesoulworks.veinmining.common.data;

import com.illusivesoulworks.veinmining.VeinMiningConstants;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.Identifier;

public class FabricBlockGroupsResourceListener extends BlockGroupsResourceListener implements
    IdentifiableResourceReloadListener {

  private static final Identifier ID =
      Identifier.fromNamespaceAndPath(VeinMiningConstants.MOD_ID, "groups");

  @Override
  public Identifier getFabricId() {
    return ID;
  }
}
