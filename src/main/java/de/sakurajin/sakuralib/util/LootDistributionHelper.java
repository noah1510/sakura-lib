package de.sakurajin.sakuralib.util;

import java.util.ArrayList;
import java.util.HashMap;

public class LootDistributionHelper {
    static final HashMap<Integer, HashMap<Integer, ArrayList<Double>>> distributionCache = new HashMap<>();

    public static ArrayList<Double> getDistribution(int order, int elementCount){
        var orderMap = distributionCache.getOrDefault(order, new HashMap<>());
        var elementMap = orderMap.getOrDefault(elementCount, new ArrayList<>());
        if(elementMap.isEmpty()){
            double sum = 0;
            for(int i= 0; i < elementCount; i++){
                sum += Math.pow(elementCount - i, order);
            }

            for(int i = 0; i < elementCount; i++){
                elementMap.add(Math.pow(elementCount - i, order) / sum);
            }
            orderMap.put(elementCount, elementMap);
            distributionCache.put(order, orderMap);
        }

        return elementMap;
    }

}
