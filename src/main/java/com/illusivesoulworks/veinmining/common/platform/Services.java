package com.illusivesoulworks.veinmining.common.platform;

import com.illusivesoulworks.veinmining.common.platform.services.IPlatform;
import com.illusivesoulworks.veinmining.platform.FabricPlatform;

/** Fabric-only: platform is always the Fabric implementation (no ServiceLoader). */
public class Services {

  public static final IPlatform PLATFORM = new FabricPlatform();

  public static <T> T load(Class<T> clazz) {
    throw new UnsupportedOperationException("ServiceLoader is not used in the Fabric port");
  }
}
