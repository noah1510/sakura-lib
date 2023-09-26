package de.sakurajin.sakuralib.loot.v1;

import de.sakurajin.sakuralib.loot.v2.distribution.PowerDistribution;

import java.util.ArrayList;

/**
 * This class can be used to create weight distributions for loot tables.
 * The following types of distributions are supported:
 * * power distribution (@see LootDistributionHelper#getPowerDistribution(int, int))
 * <p>
 * Other distributions may be added in the future.
 * @deprecated use the individual distributions directly instead. Will be removed in 2.0.0
 * @see PowerDistribution for the v2 implementation
 */
@Deprecated(since = "1.4.0", forRemoval = true)
public class LootDistributionHelper {

    /**
     * This method returns a power distribution for the given order and element count.
     * It passes the value to the v2 implementation.
     * @see PowerDistribution for more information.
     *
     * @param order        the order of the distribution
     * @param elementCount the number of elements in the distribution
     * @return the distribution
     * @throws IllegalArgumentException if elementCount is less than or equal to 0
     */
    public static ArrayList<Double> getPowerDistribution(int order, int elementCount) throws IllegalArgumentException {
        return PowerDistribution.GetDistribution(order, elementCount);
    }

}
