package com.illusivesoulworks.veinmining;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

/**
 * Code-registered Vein Mining enchantment for Minecraft 1.20.1 (before
 * enchantments became datapack-driven in 1.20.5). Mirrors the original
 * datapack definition: max level 1, weight 2, anvil cost 4, mainhand,
 * mining tools (#minecraft:enchantable/mining_loot).
 */
public class VeinMiningEnchantment extends Enchantment {

  public static final ResourceLocation ID =
      new ResourceLocation(VeinMiningConstants.MOD_ID, "vein_mining");
  public static final VeinMiningEnchantment INSTANCE = new VeinMiningEnchantment();

  private static final TagKey<net.minecraft.world.item.Item> MINING_LOOT =
      TagKey.create(Registries.ITEM,
          new ResourceLocation("minecraft", "enchantable/mining_loot"));

  protected VeinMiningEnchantment() {
    super(Rarity.COMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND);
  }

  @Override
  public int getMaxLevel() {
    return 1;
  }

  @Override
  public int getMinCost(int level) {
    return 15 + (level - 1) * 9;
  }

  @Override
  public int getMaxCost(int level) {
    return 65 + (level - 1) * 9;
  }

  @Override
  public boolean canEnchant(ItemStack stack) {
    return stack.is(MINING_LOOT);
  }

  @Override
  public boolean isTreasureOnly() {
    return false;
  }

  @Override
  public boolean isTradeable() {
    return true;
  }

  @Override
  public boolean isDiscoverable() {
    return true;
  }

  public static int getLevel(ItemStack stack) {
    return EnchantmentHelper.getItemEnchantmentLevel(INSTANCE, stack);
  }
}
