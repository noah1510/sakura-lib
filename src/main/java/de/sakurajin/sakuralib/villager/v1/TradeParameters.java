package de.sakurajin.sakuralib.villager.v1;

import com.glisco.numismaticoverhaul.currency.CurrencyHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

/**
 * A class to represent the parameters for an individual trade.
 */
public class TradeParameters{
    private static final boolean hasNumismatic = FabricLoader.getInstance().isModLoaded("numismatic-overhaul");

    public int price = 125;
    public int emeraldAmount = 1;
    public ItemConvertible item = Items.EMERALD;
    public int amount = 1;
    public int maxUses = 16;
    public int merchantExperience = 1;
    public float priceMultiplier = 0.02f;
    public boolean sell = false;
    public int tradeLevel = 1;

    /**
     * @param price The price in currency (e.g. 80 for 80 bronze coins or 200 for 2 silver coins)
     * @param emeraldAmount The amount of emeralds to be traded when numismatic-overhaul is not present
     * @param item The item to be traded
     * @param amount The amount of items to be traded
     * @param maxUses The maximum number of times this trade can be used
     * @param merchantExperience The experience given to the player when using this trade
     * @param priceMultiplier The price multiplier for this trade
     * @param sell Whether the item is sold or bought by the villager
     */
    public TradeParameters(int price, int emeraldAmount, ItemConvertible item, int amount, int maxUses, int merchantExperience, float priceMultiplier, boolean sell, int tradeLevel) {
        this.price = price;
        this.emeraldAmount = emeraldAmount;
        this.item = item;
        this.amount = amount;
        this.maxUses = maxUses;
        this.merchantExperience = merchantExperience;
        this.priceMultiplier = priceMultiplier;
        this.sell = sell;
        this.tradeLevel = tradeLevel;
    }

    /**
     * This is the constructor you should probably use.
     * It will automatically calculate the emerald amount, merchant experience and price multiplier based on the price.
     * @param price The price in currency (e.g. 80 for 80 bronze coins or 200 for 2 silver coins)
     * @param item The item to be traded
     * @param amount The amount of items to be traded
     * @param maxUses The maximum number of times this trade can be used
     * @param sell Whether the item is sold or bought by the villager
     * @param tradeLevel The level at which this trade should be unlocked
     */
    public TradeParameters(int price, ItemConvertible item, int amount, int maxUses, boolean sell, int tradeLevel) {
        this.price = price;
        this.item = item;
        this.amount = amount;
        this.maxUses = maxUses;
        this.sell = sell;
        this.tradeLevel = tradeLevel;
        this.emeraldAmount = emeraldsFromCurrency(price);
        this.merchantExperience = (int) Math.floor(price / 100f) + tradeLevel;
        this.priceMultiplier = 0.01f + 0.0025f * ((float) price / 250);
    }

    /**
     * A constructor to create a trade with the default values.
     * They can be changed afterward.
     */
    public TradeParameters(){}

    /**
     * Get the currency stack for this trade.
     * If numismatic-overhaul is present, the currency will be used.
     * Otherwise, emeralds or emerald blocks will be used depending on the cost.
     * @return the ItemStack containing the currency
     */
    public ItemStack getCurrencyStack(){
        ItemStack currency;
        if(hasNumismatic) {
            currency = CurrencyHelper.getClosest(price);
        }else{
            currency = getEmeraldStack(emeraldAmount);
        }
        return currency;
    }

    /**
     * Get the item stack for this trade.
     * @return the ItemStack containing the item with the correct amount
     */
    public ItemStack getItemStack(){
        return new ItemStack(item, amount);
    }

    /**
     * Convert a currency amount to an amount of emeralds
     * @param currency the currency amount
     * @return the amount of emeralds
     */
    public static int emeraldsFromCurrency(int currency){
        return (int) Math.ceil(currency / 125.0);
    }

    /**
     * Convert an amount of emeralds to an emerald stack
     * If the trade cost is higher than 64 emeralds, emerald blocks will be used.
     * @param amount the amount of emeralds
     * @return the ItemStack containing the emeralds
     */
    public static ItemStack getEmeraldStack(int amount){
        if (amount >= 64){
            return new ItemStack(Items.EMERALD_BLOCK, amount / 9);
        }else{
            return new ItemStack(Items.EMERALD, amount);
        }
    }
}
