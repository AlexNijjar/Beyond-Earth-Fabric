package com.github.alexnijjar.beyond_earth.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.math.Vec3d;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
    private static final double CONSTANT = -0.03;

    @Inject(method = "tick", at = @At("TAIL"), cancellable = true)
    public void tick(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (!entity.hasNoGravity()) {
            Vec3d velocity = entity.getVelocity();
            double newGravity = ModUtils.getMixinGravity(CONSTANT, this);
            entity.setVelocity(velocity.getX(), velocity.getY() - CONSTANT + newGravity, velocity.getZ());
        }
    }
}