package vin.ash.noresserver.mixins;

import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SharedConstants.class)
public class SharedConstantsMixin {
    @Inject(at = @At("HEAD"), method = "isValidChar(C)Z", cancellable = true)
    private static void isValidChar(char chr, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
