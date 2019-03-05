package gregtech.common.items;

import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.unification.ore.OrePrefix;

public class MetaMekanismComponents extends MaterialMetaItem {
	
	public MetaMekanismComponents() {
		super(OrePrefix.clump, OrePrefix.shard, OrePrefix.crystal, OrePrefix.dustDirty);
	}
}
