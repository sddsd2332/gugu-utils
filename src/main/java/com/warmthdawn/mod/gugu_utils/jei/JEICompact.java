package com.warmthdawn.mod.gugu_utils.jei;

import appeng.client.gui.implementations.*;
import appeng.fluids.client.gui.*;
import cofh.thermaldynamics.gui.client.GuiDuctConnection;
import com.blamejared.ctgui.api.SlotRecipe;
import com.blamejared.ctgui.client.gui.craftingtable.GuiCraftingTable;
import com.warmthdawn.mod.gugu_utils.ModItems;
import com.warmthdawn.mod.gugu_utils.botania.BotaniaCompact;
import com.warmthdawn.mod.gugu_utils.botania.recipes.TransformRecipe;
import com.warmthdawn.mod.gugu_utils.common.Enables;
import com.warmthdawn.mod.gugu_utils.gugucrttool.CrtToolGui;
import com.warmthdawn.mod.gugu_utils.gugucrttool.GhostJEIHandler;
import com.warmthdawn.mod.gugu_utils.jei.botania.BurstTransformCategory;
import com.warmthdawn.mod.gugu_utils.jei.botania.BurstTransformWapper;
import com.warmthdawn.mod.gugu_utils.jei.gui.*;
import com.warmthdawn.mod.gugu_utils.jei.ingedients.*;
import com.warmthdawn.mod.gugu_utils.jei.renders.*;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiFilter;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiLaserRelayItemWhitelist;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiRangedCollector;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

@JEIPlugin
public class JEICompact implements IModPlugin {

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
        if (Enables.BOTANIA)
            registry.register(() -> IngedientMana.class, new ArrayList<>(), new InfoHelper<>(), RendererMana.INSTANCE);
        if (Enables.ASTRAL_SORCERY)
            registry.register(() -> IngredientStarlight.class, new ArrayList<>(), new InfoHelper<>(), RendererStarlight.INSTANCE);
        if (Enables.EMBERS)
            registry.register(() -> IngredientEmber.class, new ArrayList<>(), new InfoHelper<>(), RendererEmber.INSTANCE);
        registry.register(() -> IngredientEnvironment.class, new ArrayList<>(), new InfoHelper<>(), RendererEnvironment.INSTANCE);

        if (Enables.NATURES_AURA)
            registry.register(() -> IngredientAura.class, new ArrayList<>(), new InfoHelper<>(), RendererAura.INSTANCE);
    }


    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        if (Enables.BOTANIA)
            registry.addRecipeCategories(new BurstTransformCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.addGhostIngredientHandler(CrtToolGui.class, new GhostJEIHandler());
        if (Enables.CRAFT_TWEAKER) {
            registry.addGhostIngredientHandler(GuiCraftingTable.class, new GenericGhostHandler<>(SlotRecipe.class));
            registry.addGhostIngredientHandler(GuiFurnace.class, new GenericGhostHandler<>(SlotRecipe.class));
        }
        if (Enables.THERMAL_DYNAMICS)
            registry.addGhostIngredientHandler(GuiDuctConnection.class, new GenericGhostHandler<>(cofh.thermaldynamics.gui.slot.SlotFilter.class));
        if (Enables.APPLIED_ENERGISTICS) {
            registry.addGhostIngredientHandler(GuiPatternTerm.class, new AEPatternGhostHandler());
            registry.addGhostIngredientHandler(GuiLevelEmitter.class, new AEGenericGhostHandler<>());
            registry.addGhostIngredientHandler(GuiStorageBus.class, new AEGenericGhostHandler<>());
            registry.addGhostIngredientHandler(GuiFormationPlane.class, new AEGenericGhostHandler<>());
            registry.addGhostIngredientHandler(GuiCellWorkbench.class, new AEGenericGhostHandler<>());
            registry.addGhostIngredientHandler(GuiInterface.class, new AEInterfaceGhostHandler());
            registry.addGhostIngredientHandler(GuiUpgradeable.class, new AEIOBusGhostHandler());


            registry.addGhostIngredientHandler(GuiFluidIO.class, new AEFluidGhostHandler<>());
            registry.addGhostIngredientHandler(GuiFluidLevelEmitter.class, new AEFluidGhostHandler<>());
            registry.addGhostIngredientHandler(GuiFluidFormationPlane.class, new AEFluidGhostHandler<>());
            registry.addGhostIngredientHandler(GuiFluidStorageBus.class, new AEFluidGhostHandler<>());
            registry.addGhostIngredientHandler(GuiFluidInterface.class, new AEFluidGhostHandler<>());

        }

        if (Enables.ACTUALLY_ADDITIONS) {
            registry.addGhostIngredientHandler(GuiLaserRelayItemWhitelist.class, new AAFilterGhostHandler<>());
            registry.addGhostIngredientHandler(GuiFilter.class, new AAFilterGhostHandler<>());
            registry.addGhostIngredientHandler(GuiRangedCollector.class, new AAFilterGhostHandler<>());
        }
//        registry.addGhostIngredientHandler(GuiCraftingTable.class, new GenericGhostHandler<>());

        if (Enables.BOTANIA) {
            registry.handleRecipes(TransformRecipe.class, BurstTransformWapper::new, BurstTransformCategory.UID);
            registry.addRecipes(BotaniaCompact.recipeBurstTransform, BurstTransformCategory.UID);
            registry.addRecipeCatalyst(new ItemStack(vazkii.botania.common.block.ModBlocks.spreader), BurstTransformCategory.UID);
            registry.addRecipeCatalyst(new ItemStack(ModItems.lensTransform), BurstTransformCategory.UID);
        }
    }

}
