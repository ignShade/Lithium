package net.leonemc.lithium.customitems.annotation;

import net.leonemc.api.items.XMaterial;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ItemInfo {

    String name();
    String displayName();
    String description();
    XMaterial displayItem();
    int uses() default 1;
}
