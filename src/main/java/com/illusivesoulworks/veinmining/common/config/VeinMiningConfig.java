package com.illusivesoulworks.veinmining.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.illusivesoulworks.veinmining.VeinMiningConstants;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import net.minecraftforge.fml.loading.FMLPaths;

/**
 * Plain-JSON replacement for the original SpectreLib config. Field names,
 * defaults and semantics are identical to the original
 * {@code VeinMiningConfig} (server + client specs); values are exposed
 * through the same {@code .get()} wrappers so all original logic code is
 * unchanged. Users edit {@code config/veinmining.json} (created with
 * defaults on first launch).
 */
public class VeinMiningConfig {

  public static final Server SERVER = new Server();
  public static final Client CLIENT = new Client();

  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public static void setup() {
    Path dir = FMLPaths.CONFIGDIR.get();
    Path file = dir.resolve(VeinMiningConstants.MOD_ID + ".json");
    Root data = new Root();
    if (Files.exists(file)) {
      try {
        String json = Files.readString(file);
        Root parsed = GSON.fromJson(json, Root.class);
        if (parsed != null) {
          data = parsed;
        }
      } catch (IOException | RuntimeException e) {
        VeinMiningConstants.LOG.warn("Failed to load config {}, using defaults", file, e);
      }
    } else {
      try {
        Files.createDirectories(dir);
        Files.writeString(file, GSON.toJson(data));
      } catch (IOException e) {
        VeinMiningConstants.LOG.warn("Failed to write default config {}", file, e);
      }
    }
    SERVER.requiredDestroySpeed.set(data.server.requiredDestroySpeed);
    SERVER.requireCorrectTool.set(data.server.requireCorrectTool);
    SERVER.maxBlocksBase.set(data.server.maxBlocksBase);
    SERVER.maxBlocksPerLevel.set(data.server.maxBlocksPerLevel);
    SERVER.diagonalMining.set(data.server.diagonalMining);
    SERVER.limitedByDurability.set(data.server.limitedByDurability);
    SERVER.relocateDrops.set(data.server.relocateDrops);
    SERVER.preventToolDestruction.set(data.server.preventToolDestruction);
    SERVER.addToolDamage.set(data.server.addToolDamage);
    SERVER.toolDamageMultiplier.set(data.server.toolDamageMultiplier);
    SERVER.addExhaustion.set(data.server.addExhaustion);
    SERVER.exhaustionMultiplier.set(data.server.exhaustionMultiplier);
    SERVER.limitedByWorld.set(data.server.limitedByWorld);
    SERVER.blocks.set(data.server.blocks);
    SERVER.blocksList.set(data.server.blocksList);
    SERVER.blocksListType.set(data.server.blocksListType);
    CLIENT.activationState.set(data.client.activationState);
    CLIENT.activationStateWithoutEnchantment.set(data.client.activationStateWithoutEnchantment);
    CLIENT.enableEnchantmentWarnings.set(data.client.enableEnchantmentWarnings);
    CLIENT.enableEnchantmentTooltips.set(data.client.enableEnchantmentTooltips);
    CLIENT.enableEnchantmentNotifications.set(data.client.enableEnchantmentNotifications);
  }

  public static class DoubleValue {
    private double value;
    public DoubleValue(double v) { this.value = v; }
    public double get() { return value; }
    public void set(double v) { this.value = v; }
  }

  public static class FloatValue {
    private float value;
    public FloatValue(float v) { this.value = v; }
    public float get() { return value; }
    public void set(float v) { this.value = v; }
  }

  public static class BooleanValue {
    private boolean value;
    public BooleanValue(boolean v) { this.value = v; }
    public boolean get() { return value; }
    public void set(boolean v) { this.value = v; }
  }

  public static class IntValue {
    private int value;
    public IntValue(int v) { this.value = v; }
    public int get() { return value; }
    public void set(int v) { this.value = v; }
  }

  public static class EnumValue<T extends Enum<T>> {
    private T value;
    public EnumValue(T v) { this.value = v; }
    public T get() { return value; }
    public void set(T v) { this.value = v; }
  }

  public static class TransformableValue {
    private List<String> value;
    public TransformableValue(List<String> v) { this.value = v; }
    public Set<String> getTransformed() { return Set.copyOf(value); }
    public void clearCache() {}
    public void set(List<String> v) { this.value = v; }
  }

  public static class Server {
    public final DoubleValue requiredDestroySpeed = new DoubleValue(1.0D);
    public final BooleanValue requireCorrectTool = new BooleanValue(false);
    public final IntValue maxBlocksBase = new IntValue(0);
    public final IntValue maxBlocksPerLevel = new IntValue(50);
    public final BooleanValue diagonalMining = new BooleanValue(true);
    public final BooleanValue limitedByDurability = new BooleanValue(true);
    public final BooleanValue relocateDrops = new BooleanValue(true);
    public final BooleanValue preventToolDestruction = new BooleanValue(true);
    public final BooleanValue addToolDamage = new BooleanValue(true);
    public final IntValue toolDamageMultiplier = new IntValue(1);
    public final BooleanValue addExhaustion = new BooleanValue(true);
    public final FloatValue exhaustionMultiplier = new FloatValue(1.0F);
    public final BooleanValue limitedByWorld = new BooleanValue(true);
    public final EnumValue<BlocksType> blocks = new EnumValue<>(BlocksType.CONFIG_LIST);
    public final TransformableValue blocksList =
        new TransformableValue(List.of("#c:ores", "#forge:ores", "#minecraft:logs"));
    public final EnumValue<ListType> blocksListType = new EnumValue<>(ListType.ALLOW);
  }

  public static class Client {
    public final EnumValue<ActivationState> activationState =
        new EnumValue<>(ActivationState.STANDING);
    public final EnumValue<ActivationState> activationStateWithoutEnchantment =
        new EnumValue<>(ActivationState.HOLD_KEY_DOWN);
    public final BooleanValue enableEnchantmentWarnings = new BooleanValue(true);
    public final BooleanValue enableEnchantmentTooltips = new BooleanValue(true);
    public final BooleanValue enableEnchantmentNotifications = new BooleanValue(false);
  }

  public static class ServerData {
    public double requiredDestroySpeed = 1.0D;
    public boolean requireCorrectTool = false;
    public int maxBlocksBase = 0;
    public int maxBlocksPerLevel = 50;
    public boolean diagonalMining = true;
    public boolean limitedByDurability = true;
    public boolean relocateDrops = true;
    public boolean preventToolDestruction = true;
    public boolean addToolDamage = true;
    public int toolDamageMultiplier = 1;
    public boolean addExhaustion = true;
    public float exhaustionMultiplier = 1.0F;
    public boolean limitedByWorld = true;
    public BlocksType blocks = BlocksType.CONFIG_LIST;
    public List<String> blocksList = List.of("#c:ores", "#forge:ores", "#minecraft:logs");
    public ListType blocksListType = ListType.ALLOW;
  }

  public static class ClientData {
    public ActivationState activationState = ActivationState.STANDING;
    public ActivationState activationStateWithoutEnchantment = ActivationState.HOLD_KEY_DOWN;
    public boolean enableEnchantmentWarnings = true;
    public boolean enableEnchantmentTooltips = true;
    public boolean enableEnchantmentNotifications = false;
  }

  public static class Root {
    public ServerData server = new ServerData();
    public ClientData client = new ClientData();
  }

  public enum ListType {
    ALLOW,
    DENY
  }

  public enum ActivationState {
    STANDING,
    CROUCHING,
    HOLD_KEY_DOWN
  }

  public enum BlocksType {
    CONFIG_LIST,
    ALL,
    ORES,
    ORES_LOGS,
    ORES_STONE,
    ORES_STONE_LOGS,
    NO_BLOCK_ENTITIES
  }
}
