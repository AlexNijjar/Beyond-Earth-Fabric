package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.SolarPanelScreenHandler;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SolarPanelBlockEntity extends AbstractMachineBlockEntity {

    public static final long MAX_ENERGY = BeyondEarth.CONFIG.solarPanel.maxEnergy;
    public static final long ENERGY_PER_TICK = BeyondEarth.CONFIG.solarPanel.energyPerTick;

    public SolarPanelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.SOLAR_PANEL, blockPos, blockState);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new SolarPanelScreenHandler(syncId, inv, this);
    }

    @Override
    public boolean usesEnergy() {
        return true;
    }

    @Override
    public long getMaxGeneration() {
        return MAX_ENERGY;
    }

    @Override
    public long getEnergyPerTick() {
        return ENERGY_PER_TICK;
    }

    @Override
    public long getMaxEnergyExtract() {
        return ENERGY_PER_TICK * 2;
    }

    @Override
    public void tick() {
        if (!this.world.isClient) {
            // Check solar panel conditions.
            if (world.isDay() && (!this.world.getRegistryKey().equals(World.OVERWORLD) || !this.world.isRaining() && !this.world.isThundering()) && world.isSkyVisible(this.getPos().up())) {
                this.cumulateEnergy();
                this.setActive(true);
            } else {
                this.setActive(false);
            }

            this.energyOut();
        }
    }
}