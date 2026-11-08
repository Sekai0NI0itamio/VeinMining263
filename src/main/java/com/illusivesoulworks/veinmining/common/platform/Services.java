package com.illusivesoulworks.veinmining.common.platform;

import com.illusivesoulworks.veinmining.common.platform.services.IPlatform;
import com.illusivesoulworks.veinmining.platform.ForgePlatform;

/** Forge port: platform is always the Forge implementation (no ServiceLoader). */
public class Services {

  public static final IPlatform PLATFORM = new ForgePlatform();

  public static <T> T load(Class<T> clazz) {
    throw new UnsupportedOperationException("ServiceLoader is not used in the Forge port");
  }
}
