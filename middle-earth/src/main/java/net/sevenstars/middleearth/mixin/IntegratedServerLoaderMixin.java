package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldOpenFlows.class)
public abstract class IntegratedServerLoaderMixin {
    @ModifyVariable(method = "confirmWorldCreation" ,at = @At("HEAD"), index = 4, argsOnly = true)
    private static boolean removeAdviceOnCreation(boolean original) {
        return true;
    }

    @ModifyVariable( method = "openWorldLoadLevelStem(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lcom/mojang/serialization/Dynamic;ZLjava/lang/Runnable;)V",
            at = @At("HEAD"), index = 3, argsOnly = true)
    private boolean removeAdviceOnLoad(boolean original) {
        return false;
    }

    @Shadow protected abstract void openWorldLoadBundledResourcePack(LevelStorageSource.LevelStorageAccess session, WorldStem saveLoader, PackRepository dataPackManager, Runnable onCancel);

    @Redirect(method = "openWorldCheckWorldStemCompatibility", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows;askForBackup(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;ZLjava/lang/Runnable;Ljava/lang/Runnable;)V"))
    private void checkBackupAndStart(WorldOpenFlows instance, LevelStorageSource.LevelStorageAccess session, boolean customized,
                                     Runnable callback, Runnable onCancel, @Local WorldStem saveLoader,
                                     @Local PackRepository dataPackManager) {
        this.openWorldLoadBundledResourcePack(session, saveLoader, dataPackManager, onCancel);
    }
}
