package com.illusivesoulworks.veinmining.mixin.core;

import com.illusivesoulworks.veinmining.mixin.VeinMiningMixinHooks;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class MixinItemStack {

  @Inject(
      at = @At(
          value = "INVOKE",
          target = "net/minecraft/world/item/ItemStack.isDamageableItem()Z"),
      method = "hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V",
      cancellable = true)
  private void veinmining$itemDamage(int amount,
                                     @Nullable LivingEntity entity, Consumer<Item> consumer,
                                     CallbackInfo ci) {

    if (VeinMiningMixinHooks.shouldCancelItemDamage(
        entity instanceof ServerPlayer serverPlayer ? serverPlayer : null)) {
      ci.cancel();
    }
  }

  @SuppressWarnings("ConstantConditions")
  @ModifyVariable(
      at = @At("HEAD"),
      method = "hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V",
      argsOnly = true
  )
  private int veinmining$changeBreak(int amount, int unused,
                                     @Nullable LivingEntity entity) {

    return VeinMiningMixinHooks.modifyItemDamage((ItemStack) (Object) this, amount,
        entity instanceof ServerPlayer serverPlayer ? serverPlayer : null);
  }
}
