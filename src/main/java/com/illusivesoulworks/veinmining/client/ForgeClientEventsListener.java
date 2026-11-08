package com.illusivesoulworks.veinmining.client;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeClientEventsListener {

  @SubscribeEvent
  @SuppressWarnings("unused")
  public void veinMiningState(final TickEvent.ClientTickEvent evt) {
    if (evt.phase == TickEvent.Phase.END) {
      VeinMiningClientEvents.tick();
    }
  }

  @SubscribeEvent
  @SuppressWarnings("unused")
  public void itemTooltip(final ItemTooltipEvent evt) {
    VeinMiningClientEvents.tooltip(evt.getItemStack(), evt.getToolTip());
  }
}
