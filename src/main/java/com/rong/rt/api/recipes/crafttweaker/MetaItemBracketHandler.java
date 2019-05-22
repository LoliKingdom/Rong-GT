package com.rong.rt.api.recipes.crafttweaker;

import java.util.List;

import com.rong.rt.api.metaitems.MetaItem;
import com.rong.rt.api.metaitems.MetaItem.MetaValueItem;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;

@BracketHandler
@ZenRegister
public class MetaItemBracketHandler implements IBracketHandler {

    private final IJavaMethod method;

    public MetaItemBracketHandler() {
        this.method = CraftTweakerAPI.getJavaMethod(MetaItemBracketHandler.class, "getMetaItem", String.class);
    }

    public static IItemStack getMetaItem(String name) {
        MetaValueItem targetItem = MetaItem.getMetaItems().stream()
            .flatMap(item -> item.getAllItems().stream())
            .map(item -> (MetaValueItem) item)
            .filter(item -> item.unlocalizedName.equals(name))
            .findFirst().orElse(null);
        return targetItem == null ? null : new MCItemStack(targetItem.getStackForm());
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if ((tokens.size() < 3)) return null;
        if (!tokens.get(0).getValue().equalsIgnoreCase("metaitem")) return null;
        if (!tokens.get(1).getValue().equals(":")) return null;
        StringBuilder nameBuilder = new StringBuilder();
        for(int i = 2; i < tokens.size(); i++) {
            nameBuilder.append(tokens.get(i).getValue());
        }
        return position -> new ExpressionCallStatic(position, environment, method,
            new ExpressionString(position, nameBuilder.toString()));
    }

}
