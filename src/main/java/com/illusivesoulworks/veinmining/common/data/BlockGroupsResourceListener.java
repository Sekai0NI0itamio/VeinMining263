package com.illusivesoulworks.veinmining.common.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.illusivesoulworks.veinmining.VeinMiningConstants;
import com.illusivesoulworks.veinmining.common.veinmining.VeinMiningEvents;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;

/**
 * 1.21.11: SimpleJsonResourceReloadListener is Codec-based, so the
 * block-group files (data/<namespace>/veinmining/groups.json) are loaded
 * directly with a Fabric reload listener. Same parsing as upstream.
 */
public class BlockGroupsResourceListener implements IdentifiableResourceReloadListener {

  public static BlockGroupsResourceListener INSTANCE;

  private List<Set<String>> groups = new ArrayList<>();

  @Override
  public void reload(PreparableReloadListener.SharedState sharedState, Executor executor,
                     PreparableReloadListener.PreparationBarrier preparationBarrier,
                     Executor executor2) {
    ResourceManager resourceManager = sharedState.resourceManager();
    List<Set<String>> blocks = new ArrayList<>();
    Map<Identifier, Resource> files = resourceManager.listResources(
        "veinmining", id -> id.getPath().endsWith("groups.json"));

    for (Map.Entry<Identifier, Resource> entry : files.entrySet()) {
      try (InputStream stream = entry.getValue().open()) {
        String json = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        JsonElement element = JsonParser.parseString(json);
        fromJson(blocks, GsonHelper.convertToJsonObject(element, "top element"));
      } catch (Exception e) {
        VeinMiningConstants.LOG.error("Parsing error loading veinmining groups {}",
            entry.getKey(), e);
      }
    }
    this.groups = blocks;
    VeinMiningEvents.reloadDatapack();
  }

  @Override
  public Identifier getFabricId() {
    return Identifier.fromNamespaceAndPath(VeinMiningConstants.MOD_ID, "groups");
  }

  public List<Set<String>> getGroups() {
    return Collections.unmodifiableList(this.groups);
  }

  private static void fromJson(List<Set<String>> blocks, JsonObject jsonObject)
      throws IllegalArgumentException, JsonParseException {
    boolean replace = GsonHelper.getAsBoolean(jsonObject, "replace", false);
    JsonArray groups = GsonHelper.getAsJsonArray(jsonObject, "groups", new JsonArray());

    if (replace) {
      blocks.clear();
    }

    for (JsonElement element : groups) {

      if (element.isJsonArray()) {
        JsonArray jsonArray = element.getAsJsonArray();
        Set<String> newBlocks = new HashSet<>();

        for (JsonElement element1 : jsonArray) {

          if (element1 instanceof JsonPrimitive) {
            String block = element1.getAsString();
            newBlocks.add(block);
          }
        }
        blocks.add(newBlocks);
      }
    }
  }
}
