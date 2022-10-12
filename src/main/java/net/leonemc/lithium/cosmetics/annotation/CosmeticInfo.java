package net.leonemc.lithium.cosmetics.annotation;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.cosmetics.objects.CosmeticRarity;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CosmeticInfo {
    String name();
    String description();
    XMaterial displayItem();
    CosmeticType cosmeticType();
    String permission() default "";
    int price() default -1;
    CosmeticRarity rarity();
    CosmeticGroup group() default CosmeticGroup.PURCHASE;
}
