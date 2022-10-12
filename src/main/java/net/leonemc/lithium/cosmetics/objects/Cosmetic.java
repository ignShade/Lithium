package net.leonemc.lithium.cosmetics.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.cosmetics.annotation.CosmeticInfo;

@Data
@AllArgsConstructor
public abstract class Cosmetic {

    private String name;
    private String description;
    private XMaterial displayItem;
    private CosmeticType cosmeticType;
    private String permission;
    private int price;
    private CosmeticRarity rarity;
    private CosmeticGroup group;

    public Cosmetic(String name,
                    String description,
                    XMaterial displayItem,
                    CosmeticType cosmeticType,
                    int price,
                    CosmeticRarity rarity,
                    CosmeticGroup group) {

        this(name, description, displayItem, cosmeticType, null, price, rarity, group);
    }

    public Cosmetic() {
        name = getClass().getAnnotation(CosmeticInfo.class).name();
        description = getClass().getAnnotation(CosmeticInfo.class).description();
        displayItem = getClass().getAnnotation(CosmeticInfo.class).displayItem();
        cosmeticType = getClass().getAnnotation(CosmeticInfo.class).cosmeticType();
        permission = getClass().getAnnotation(CosmeticInfo.class).permission();
        rarity = getClass().getAnnotation(CosmeticInfo.class).rarity();
        group = getClass().getAnnotation(CosmeticInfo.class).group();
        price = getClass().getAnnotation(CosmeticInfo.class).price();
    }
}