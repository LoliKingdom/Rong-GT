package com.rong.rt.common;

import com.rong.rt.RTLog;
import com.rong.rt.Values;
import com.rong.rt.common.blocks.RTBlocks;

import ic2.core.platform.textures.Ic2Icons;
import ic2.core.platform.textures.Sprites;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RTSprites {

	@SideOnly(Side.CLIENT)
	public static void loadSprites() {
		RTLog.logger.info("Registering Bender Sprite");
		registerSprite(RTBlocks.blockBender.name);
		RTLog.logger.info("Registering Autoclave Sprite");
		registerSprite(RTBlocks.blockAutoclave.name);
	}

	private static void registerSprite(String string) {
		Ic2Icons.addSprite(new Sprites.SpriteData(string, Values.MOD_ID + ":textures/sprites/" + string + ".png",
				new Sprites.SpriteInfo(1, 12)));
		Ic2Icons.addTextureEntry(new Sprites.TextureEntry(string, 0, 0, 1, 12));
	}

}
