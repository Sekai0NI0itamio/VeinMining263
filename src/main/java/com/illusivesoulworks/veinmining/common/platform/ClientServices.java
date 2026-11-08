package com.illusivesoulworks.veinmining.common.platform;

import com.illusivesoulworks.veinmining.common.platform.services.IClientPlatform;
import com.illusivesoulworks.veinmining.platform.FabricClientPlatform;

/** Fabric-only: client platform is always the Fabric implementation. */
public class ClientServices {

  public static final IClientPlatform PLATFORM = new FabricClientPlatform();
}
