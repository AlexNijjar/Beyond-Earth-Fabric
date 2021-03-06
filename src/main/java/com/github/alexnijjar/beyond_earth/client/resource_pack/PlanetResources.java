package com.github.alexnijjar.beyond_earth.client.resource_pack;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.client.BeyondEarthClient;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

@Environment(EnvType.CLIENT)
public class PlanetResources {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void register() {

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {

            @Override
            public Identifier getFabricId() {
                return new ModIdentifier("planet_resources");
            }

            @Override
            public void reload(ResourceManager manager) {

                List<SkyRenderer> skyRenderers = new LinkedList<>();
                List<SolarSystem> solarSystems = new LinkedList<>();

                // Sky Renderers.
                for (Identifier id : manager.findResources("planet_resources/sky_renderers", path -> path.endsWith(".json"))) {
                    try {
                        for (Resource resource : manager.getAllResources(id)) {
                            InputStreamReader reader = new InputStreamReader(resource.getInputStream());
                            JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);

                            if (jsonObject != null) {
                                skyRenderers.add(SkyRendererParser.parse(jsonObject));
                            }
                        }
                    } catch (Exception e) {
                        BeyondEarth.LOGGER.error("Failed to load Beyond Earth sky rendering assets from: \"" + id.toString() + "\"", e);
                        e.printStackTrace();
                    }
                }

                // Solar Systems.
                for (Identifier id : manager.findResources("planet_resources/solar_systems", path -> path.endsWith(".json"))) {
                    try {
                        for (Resource resource : manager.getAllResources(id)) {
                            InputStreamReader reader = new InputStreamReader(resource.getInputStream());
                            JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);

                            if (jsonObject != null) {
                                solarSystems.add(SolarSystemParser.parse(jsonObject));
                            }
                        }
                    } catch (Exception e) {
                        BeyondEarth.LOGGER.error("Failed to load Beyond Earth solar system assets from: \"" + id.toString() + "\"", e);
                        e.printStackTrace();
                    }
                }

                BeyondEarthClient.skyRenderers = skyRenderers;
                BeyondEarthClient.solarSystems = solarSystems;
                BeyondEarthClient.postAssetRegister();
            }
        });
    }
}
