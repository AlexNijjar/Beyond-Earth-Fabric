package com.github.alexnijjar.beyond_earth.compat.rei.compressor;

import java.util.List;

import com.github.alexnijjar.beyond_earth.compat.rei.REICategories;
import com.github.alexnijjar.beyond_earth.recipes.CompressingRecipe;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public record CompressorDisplay(CompressingRecipe recipe) implements Display {

    @Override
    public List<EntryIngredient> getInputEntries() {
        return EntryIngredients.ofIngredients(recipe.getIngredients());
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.of(recipe.getOutput()));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return REICategories.COMPRESSOR_CATEGORY;
    }
}