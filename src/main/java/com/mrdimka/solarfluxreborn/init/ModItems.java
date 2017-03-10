package com.mrdimka.solarfluxreborn.init;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.google.common.collect.Lists;
import com.mrdimka.solarfluxreborn.SolarFluxRebornMod;
import com.mrdimka.solarfluxreborn.config.ModConfiguration;
import com.mrdimka.solarfluxreborn.items.CraftingItem;
import com.mrdimka.solarfluxreborn.items.UpgradeItem;
import com.mrdimka.solarfluxreborn.reference.Reference;
import com.mrdimka.solarfluxreborn.utility.Lang;

public class ModItems {
    public static final Item mirror = new CraftingItem("mirror");
    public static final Item solarCell1 = new CraftingItem("solarCell1");
    public static final Item solarCell2 = new CraftingItem("solarCell2");
    public static final Item solarCell3 = new CraftingItem("solarCell3");
    public static final Item solarCell4 = new CraftingItem("solarCell4");

    // Upgrades
    public static Item mUpgradeBlank;
    public static Item mUpgradeEfficiency;
    public static Item mUpgradeLowLight;
    public static Item mUpgradeTraversal;
    public static Item mUpgradeTransferRate;
    public static Item mUpgradeCapacity;
    public static Item mUpgradeFurnace;

    private ModItems() {
    }

    public static void initialize() {
        register(mirror, "mirror");
        register(solarCell1, "solarCell1");
        register(solarCell2, "solarCell2");
        register(solarCell3, "solarCell3");
        register(solarCell4, "solarCell4");

        boolean anyUpgrade = false;
        if (ModConfiguration.isEfficiencyUpgradeActive()) {
            List<String> infos = Lists.newArrayList();
            infos.add(String.format(Lang.localise("upgrade.efficiency"), ModConfiguration.getEfficiencyUpgradeIncrease() * 100));
            infos.add(localiseReturnsToScale(ModConfiguration.getEfficiencyUpgradeReturnsToScale()));
            mUpgradeEfficiency = new UpgradeItem("upgradeEfficiency", ModConfiguration.getEfficiencyUpgradeMax(), infos);
            register(mUpgradeEfficiency, "upgradeEfficiency");
            anyUpgrade = true;
        }
        if (ModConfiguration.isLowLightUpgradeActive()) {
            mUpgradeLowLight = new UpgradeItem("upgradeLowLight", ModConfiguration.getLowLightUpgradeMax(), Lists.newArrayList(Lang.localise("upgrade.low.light")));
            register(mUpgradeLowLight, "upgradeLowLight");
            anyUpgrade = true;
        }
        if (ModConfiguration.isTraversalUpgradeActive()) {
            mUpgradeTraversal = new UpgradeItem(
                    "upgradeTraversal",
                    ModConfiguration.getTraversalUpgradeMax(),
                    Lists.newArrayList(String.format(Lang.localise("upgrade.traversal"), ModConfiguration.getTraversalUpgradeIncrease())));
            register(mUpgradeTraversal, "upgradeTraversal");
            anyUpgrade = true;
        }
        if(ModConfiguration.isTransferRateUpgradeActive()) {
            List<String> infos = Lists.newArrayList();
            infos.add(String.format(Lang.localise("upgrade.transfer"), ModConfiguration.getTransferRateUpgradeIncrease() * 100));
            infos.add(localiseReturnsToScale(ModConfiguration.getTransferRateUpgradeReturnsToScale()));
            mUpgradeTransferRate = new UpgradeItem("upgradeTransferRate", ModConfiguration.getTransferRateUpgradeMax(), infos);
            register(mUpgradeTransferRate, "upgradeTransferRate");
            anyUpgrade = true;
        }
        
        if(ModConfiguration.isCapacityUpgradeActive())
        {
            List<String> infos = Lists.newArrayList();
            infos.add(String.format(Lang.localise("upgrade.capacity"), ModConfiguration.getCapacityUpgradeIncrease() * 100));
            infos.add(localiseReturnsToScale(ModConfiguration.getCapacityUpgradeReturnsToScale()));
            mUpgradeCapacity = new UpgradeItem("upgradeCapacity", ModConfiguration.getCapacityUpgradeMax(), infos);
            register(mUpgradeCapacity, "upgradeCapacity");
            anyUpgrade = true;
        }
        
        if(ModConfiguration.isFurnaceUpgradeActive())
        {
            mUpgradeFurnace = new UpgradeItem("upgradeFurnace", 1, Lists.newArrayList(Lang.localise("upgrade.furnace")));
            register(mUpgradeFurnace, "upgradeFurnace");
            anyUpgrade = true;
        }
        
        if(anyUpgrade)
        {
            mUpgradeBlank = new CraftingItem("upgradeBlank");
            register(mUpgradeBlank, "upgradeBlank");
        }
    }

    private static String localiseReturnsToScale(float pValue) {
        if (pValue < 1) {
            return Lang.localise("decreasingReturnsToScale");
        } else if (pValue > 1) {
            return Lang.localise("increasingReturnsToScale");
        }
        return Lang.localise("constantReturnsToScale");
    }
    
    public static Item register(Item item, String name)
    {
    	item.setRegistryName(Reference.MOD_ID, name);
    	return GameRegistry.register(item);
    }
}
