package com.mrdimka.solarfluxreborn.blocks.modules;

import java.util.LinkedList;
import java.util.Set;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mrdimka.solarfluxreborn.config.ModConfiguration;
import com.mrdimka.solarfluxreborn.init.ModItems;
import com.mrdimka.solarfluxreborn.te.SolarPanelTileEntity;

public class TraversalEnergyDispenserModule extends SimpleEnergyDispenserModule {
    private final Set<BlockPos> mVisitedBlocks = Sets.newHashSet();
    private final LinkedList<BlockPos> mBlocksToVisit = Lists.newLinkedList();
    private int mDirectNeighborDiscovered;

    public TraversalEnergyDispenserModule(SolarPanelTileEntity pTileEntity) {
        super(pTileEntity);
    }

    protected void searchTargets() {
        if (searchFinished()) {
            // We have our new targets.
            getTargets().clear();
            getTargets().addAll(mVisitedBlocks);
            // Reset the search.
            mVisitedBlocks.clear();
            discoverNeighbors(getTileEntity().getPos());
            mDirectNeighborDiscovered = mBlocksToVisit.size();
        }

        // If we reached the max number of target we stop the search.
        if (mVisitedBlocks.size() >= mDirectNeighborDiscovered + getMaximumExtraTargets()) {
            mBlocksToVisit.clear();
        }

        progressSearch();
    }

    @Override
    protected int getTargetRefreshRate() {
        return ModConfiguration.getTraversalUpgradeUpdateRate();
    }

    private void progressSearch() {
        if (!searchFinished()) {
            // TODO use pop for DFS
            BlockPos position = mBlocksToVisit.remove();
            mVisitedBlocks.add(position);
            discoverNeighbors(position);
        }
    }

    private boolean searchFinished() {
        return mBlocksToVisit.isEmpty();
    }

    /**
     * Discover blocks that should be traveled around the location provided.
     */
    private void discoverNeighbors(BlockPos pPosition) {
        for (EnumFacing direction : EnumFacing.VALUES) {
            BlockPos neighbor = pPosition.offset(direction);
            if (!mVisitedBlocks.contains(neighbor) && isValidTarget(neighbor)) {
                mBlocksToVisit.add(neighbor);
            }
        }
    }

    /**
     * Returns the maximum amount of target that can be found in addition to the 4 neighbors of the block.
     */
    public int getMaximumExtraTargets() {
        return getTileEntity().getUpgradeCount(ModItems.mUpgradeTraversal) * ModConfiguration.getTraversalUpgradeIncrease();
    }
}
