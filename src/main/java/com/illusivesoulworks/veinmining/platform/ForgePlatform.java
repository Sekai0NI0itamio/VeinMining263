package com.illusivesoulworks.veinmining.platform;

import com.illusivesoulworks.veinmining.common.config.VeinMiningConfig;
import com.illusivesoulworks.veinmining.common.network.SPacketNotify;
import com.illusivesoulworks.veinmining.common.network.VeinMiningForgeNetwork;
import com.illusivesoulworks.veinmining.common.platform.services.IPlatform;
import com.illusivesoulworks.veinmining.common.veinmining.VeinMiningPlayers;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.network.PacketDistributor;

/** Forge port of the platform layer (1.20.1). Logic mirrors the original
 *  ForgePlatform / FabricPlatform harvest implementation. */
public class ForgePlatform implements IPlatform {

  @Override
  public void sendNotifyS2C(ServerPlayer player) {
    VeinMiningForgeNetwork.get().sendTo(player, SPacketNotify.INSTANCE);
  }

  @Override
  public Set<String> getBlocksFromTag(ResourceLocation resourceLocation) {
    Set<String> result = new HashSet<>();
    BuiltInRegistries.BLOCK.getTag(TagKey.create(Registries.BLOCK, resourceLocation))
        .ifPresent(block -> {
          for (Holder<Block> blockHolder : block) {
            blockHolder.unwrapKey().ifPresent(key -> result.add(key.location().toString()));
          }
        });
    return result;
  }

  @Override
  public Block getBlock(ResourceLocation resourceLocation) {
    return BuiltInRegistries.BLOCK.get(resourceLocation);
  }

  @Override
  public Optional<ResourceLocation> getResourceLocation(Block block) {
    return Optional.of(BuiltInRegistries.BLOCK.getKey(block));
  }

  @Override
  public boolean canHarvestDrops(ServerPlayer playerEntity, BlockState state, BlockPos blockPos) {
    return playerEntity.hasCorrectToolForDrops(state);
  }

  @Override
  public boolean harvest(ServerPlayer player, BlockPos pos, BlockPos originPos) {
    Level level = player.level();
    BlockState blockState = level.getBlockState(pos);
    GameType gameType = player.gameMode.getGameModeForPlayer();
    int exp = ForgeHooks.onBlockBreakEvent(level, gameType, player, pos);

    if (exp == -1) {
      return false;
    }
    if (!player.getMainHandItem().getItem().canAttackBlock(blockState, level, pos, player)) {
      return false;
    }
    BlockEntity blockEntity = level.getBlockEntity(pos);
    Block block = blockState.getBlock();

    if (block instanceof GameMasterBlock && !player.canUseGameMasterBlocks()) {
      level.sendBlockUpdated(pos, blockState, blockState, 3);
      return false;
    }
    if (player.blockActionRestricted(level, pos, gameType)) {
      return false;
    }
    block.playerWillDestroy(level, pos, blockState, player);
    boolean removed = level.removeBlock(pos, false);

    if (removed) {
      block.destroy(level, pos, blockState);
    }
    if (player.isCreative()) {
      return true;
    }
    ItemStack itemStack = player.getMainHandItem();
    ItemStack itemStackCopy = itemStack.copy();
    boolean canHarvest = player.hasCorrectToolForDrops(blockState);
    // 1.20.1 has no ItemStack.mineBlock; damage the tool directly
    // (VeinMiningMixinHooks still adjusts the amount via the mixin).
    itemStack.hurtAndBreak(1, player, p -> {});

    if (removed && canHarvest) {
      BlockPos spawnPos = VeinMiningConfig.SERVER.relocateDrops.get() ? originPos : pos;
      FoodData foodData = player.getFoodData();
      float currentExhaustion = foodData.getExhaustionLevel();
      VeinMiningPlayers.addMiningBlock(level, pos, spawnPos);
      block.playerDestroy(level, player, pos, blockState, blockEntity, itemStackCopy);
      VeinMiningPlayers.removeMiningBlock(level, pos);

      if (VeinMiningConfig.SERVER.addExhaustion.get()) {
        float diff = foodData.getExhaustionLevel() - currentExhaustion;
        foodData.setExhaustion(currentExhaustion);
        foodData.addExhaustion(
            (float) (diff * VeinMiningConfig.SERVER.exhaustionMultiplier.get()));
      } else {
        foodData.setExhaustion(currentExhaustion);
      }
      if (exp > 0 && level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
        blockState.getBlock().popExperience(serverLevel, spawnPos, exp);
      }
    }
    return true;
  }
}
