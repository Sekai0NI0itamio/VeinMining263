package com.illusivesoulworks.veinmining.common.platform;

import com.illusivesoulworks.veinmining.common.platform.services.IClientPlatform;
import com.illusivesoulworks.veinmining.platform.ForgeClientPlatform;

/** Forge port: client platform is always the Forge implementation. */
public class ClientServices {

  public static final IClientPlatform PLATFORM = new ForgeClientPlatform();
}
