package cn.nukkitmot.exampleplugin.custom.item;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustom;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;
import cn.nukkit.item.customitem.data.ItemCreativeGroup;
import cn.nukkit.item.customitem.data.RenderOffsets;

public class CandyCaneSword extends ItemCustom {
    private static String spacenameId = "nukkit:candy_cane_sword";
    private static String textureName = "candy_cane_sword";
    private static String name = null;

    public CandyCaneSword() {
        super(spacenameId, name, textureName);
    }

    public int scaleOffset() {
        return 32; // 需要是16的倍数，如 32、64、128
    }

    /**
     * 该方法设置自定义物品的定义
     */
    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .simpleBuilder(this, ItemCreativeCategory.EQUIPMENT)
                .creativeGroup(ItemCreativeGroup.SWORD)
                .allowOffHand(true)
                .handEquipped(true)
                .renderOffsets(RenderOffsets.scaleOffset(scaleOffset()))
                .build();
    }

    @Override
    public int getMaxDurability() {
        return 500;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getAttackDamage() {
        return 4;
    }

    @Override
    public boolean isSword() {
        return true;
    }
}