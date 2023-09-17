package de.sakurajin.sakuralib.villager.v1;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

import java.util.ArrayList;

/**
 * A helper class to create trades for villagers.
 * It will automatically use currency instead of emeralds if numismatic-overhaul is present.
 * The prices can either be set using emeralds or currency and will be converted automatically.
 * The use of currency is recommended as it will automatically scale with the currency value and will
 * automatically use emerald blocks if the price is higher than 64 emeralds.
 * The registerTrades method can be used to register all trades for a profession at once using a list of TradeParameters.
 *
 * @see TradeParameters to see the options for the individual trades
 */
public class TradeHelper {
    /**
     * Creates a trade offer for a villager
     * If numismatic-overhaul is present currency will be used instead of emeralds.
     * The amount of emeralds may be larger than 64, in which case emerald blocks will be used.
     * @param parameters The TradeParameters for the trade
     * @return The trade offer to be added to the factory
     */
    public static TradeOffer createOffer(TradeParameters parameters) {
        if(parameters.sell){
            return new TradeOffer(parameters.getCurrencyStack(), parameters.getItemStack(), parameters.maxUses, parameters.merchantExperience, parameters.priceMultiplier);
        }else{
            return new TradeOffer(parameters.getItemStack(), parameters.getCurrencyStack(), parameters.maxUses, parameters.merchantExperience, parameters.priceMultiplier);
        }
    }

    /**
     * Register all trades for a profession from a list of trade parameters
     * @param profession The profession to register the trade for
     * @param trades A list with all trades the villager should have
     */
    public static void registerTrades(VillagerProfession profession, ArrayList<TradeParameters> trades){
        for(int level = 1; level <= 5; level++) {
            final int currentLevel = level;
            ArrayList<TradeOffer> offers = trades.stream().filter(t -> t.tradeLevel == currentLevel).collect(ArrayList::new, (list, item) -> list.add(createOffer(item)), ArrayList::addAll);
            TradeOfferHelper.registerVillagerOffers(profession, currentLevel, factories -> offers.forEach(tradeOffer -> factories.add((entity, random) -> tradeOffer)));
        }
    }
}
