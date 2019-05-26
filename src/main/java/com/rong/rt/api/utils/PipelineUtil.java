package com.rong.rt.api.utils;

import org.apache.commons.lang3.ArrayUtils;

import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;

public class PipelineUtil {

    public static IVertexOperation[] color(IVertexOperation[] ops, int rgbColor) {
        return ArrayUtils.add(ops, new ColourMultiplier(Utility.convertRGBtoOpaqueRGBA_CL(rgbColor)));
    }

}
