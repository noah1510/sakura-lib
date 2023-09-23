package de.sakurajin.sakuralib.loot.v1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class can be used to create weight distributions for loot tables.
 * The following types of distributions are supported:
 * * power distribution (@see LootDistributionHelper#getPowerDistribution(int, int))
 * <p>
 * Other distributions may be added in the future.
 */
public class LootDistributionHelper {
    /**
     * This method contains a cache for previously calculated power distributions.
     * The cache is structured as follows:
     * * the first key is the order of the distribution
     * * the second key is the number of elements in the distribution
     * * the third is an array with the values of the distribution
     */
    static final HashMap<Integer, HashMap<Integer, ArrayList<Double>>> powerDistributionCache = new HashMap<>();

    /**
     * This method returns a power distribution for the given order and element count.
     * The distribution is normalized and the sum of all elements is roughly 1.
     * The distribution is cached, so it is not necessary to cache it yourself.
     * <p>
     * It is calculated using the following formula:
     * * totalWeight = sum(i = 0, elementCount - 1, (elementCount - i) ^ order)
     * * elementWeight(i) = (elementCount - i) ^ order / totalWeight
     * The totalWeight is used to normalize the distribution.
     *
     * @param order        the order of the distribution
     * @param elementCount the number of elements in the distribution
     * @return the distribution
     * @throws IllegalArgumentException if elementCount is less than or equal to 0
     */
    public static ArrayList<Double> getPowerDistribution(int order, int elementCount) throws IllegalArgumentException {
        if (elementCount <= 0) {
            throw new IllegalArgumentException("elementCount must be greater than 0");
        }

        var orderMap   = powerDistributionCache.getOrDefault(order, new HashMap<>());
        var elementMap = orderMap.getOrDefault(elementCount, new ArrayList<>());

        if (elementMap.isEmpty()) {
            double totalWeight = 0;
            for (int i = 0; i < elementCount; i++) {
                totalWeight += Math.pow(elementCount - i, order);
            }

            for (int i = 0; i < elementCount; i++) {
                elementMap.add(Math.pow(elementCount - i, order) / totalWeight);
            }
            orderMap.put(elementCount, elementMap);
            powerDistributionCache.put(order, orderMap);
        }

        return elementMap;
    }

}
