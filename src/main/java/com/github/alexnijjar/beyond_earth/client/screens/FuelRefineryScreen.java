package com.github.alexnijjar.beyond_earth.client.screens;

import java.awt.Rectangle;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.FuelRefineryBlockEntity;
import com.github.alexnijjar.beyond_earth.gui.GuiUtil;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.FuelRefineryScreenHandler;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FuelRefineryScreen extends AbstractMachineScreen<FuelRefineryScreenHandler> {

    private static final Identifier TEXTURE = new ModIdentifier("textures/screens/fuel_refinery.png");

    public static final int INPUT_TANK_LEFT = 9;
    public static final int INPUT_TANK_TOP = 21;

    public static final int OUTPUT_TANK_LEFT = 75;
    public static final int OUTPUT_TANK_TOP = 21;

    public static final int ENERGY_LEFT = 144;
    public static final int ENERGY_TOP = 21;

    public static final int ARROW_LEFT = 48;
    public static final int ARROW_TOP = 36;

    public FuelRefineryScreen(FuelRefineryScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.backgroundWidth = 177;
        this.backgroundHeight = 172;
        this.playerInventoryTitleY = this.backgroundHeight - 92;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

        super.drawBackground(matrices, delta, mouseX, mouseY);
        
        FuelRefineryBlockEntity entity = (FuelRefineryBlockEntity) blockEntity;
        
        GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration());
        GuiUtil.drawFluidTank(matrices, this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP, entity.fluidStorage.getAmount(), entity.fluidStorage.getCapacity(), entity.fluidStorage.getResource());
        GuiUtil.drawFluidTank(matrices, this.x + OUTPUT_TANK_LEFT, this.y + OUTPUT_TANK_TOP, entity.fluidStorage.getAmount(), entity.fluidStorage.getCapacity(), entity.fluidStorage.getResource());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        FuelRefineryBlockEntity entity = (FuelRefineryBlockEntity) blockEntity;

        // Energy tooltip.
        if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, Text.translatable("gauge_text.beyond_earth.storage", this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration()), mouseX, mouseY);
        }

        if (GuiUtil.isHovering(this.getInputTankBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, Text.translatable("gauge_text.beyond_earth.liquid_storage", ModUtils.dropletsToMillibuckets(entity.fluidStorage.getAmount()), ModUtils.dropletsToMillibuckets(entity.fluidStorage.getCapacity())), mouseX, mouseY);
        }

        if (GuiUtil.isHovering(this.getOutputTankBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, Text.translatable("gauge_text.beyond_earth.liquid_storage", ModUtils.dropletsToMillibuckets(entity.fluidStorage.getAmount()), ModUtils.dropletsToMillibuckets(entity.fluidStorage.getCapacity())), mouseX, mouseY);
        }
    }

    public Rectangle getInputTankBounds() {
        return GuiUtil.getFluidTankBounds(this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP);
    }

    public Rectangle getOutputTankBounds() {
        return GuiUtil.getFluidTankBounds(this.x + OUTPUT_TANK_LEFT, this.y + OUTPUT_TANK_TOP);
    }

    public Rectangle getEnergyBounds() {
        return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
    }
}