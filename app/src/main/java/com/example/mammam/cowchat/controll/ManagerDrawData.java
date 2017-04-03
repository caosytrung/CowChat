package com.example.mammam.cowchat.controll;

import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.ItemPenColor;
import com.example.mammam.cowchat.models.ItemPenWitdth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dee on 10/03/2017.
 */

public class ManagerDrawData implements IConstand{
    public static List<ItemPenWitdth> getItemPenWidth(){
        List<ItemPenWitdth> itemPenWitdths = new ArrayList<>();
        itemPenWitdths.add(new ItemPenWitdth(SMALL_WIDTH));
        itemPenWitdths.add(new ItemPenWitdth(LARGE_WIDTH));
        itemPenWitdths.add(new ItemPenWitdth(BIG_WIDTH));
        return itemPenWitdths;
    }

    public static List<ItemPenColor> getItemPenColors(){
        List<ItemPenColor> itemPenColors = new ArrayList<>();
        itemPenColors.add(new ItemPenColor(GREEN_COLOR));
        itemPenColors.add(new ItemPenColor(YELLOW_COLOR));
        itemPenColors.add(new ItemPenColor(BLACK_COLOR));
        itemPenColors.add(new ItemPenColor(WHITE_COLOR));
        itemPenColors.add(new ItemPenColor(ORANGE_COLOR));
        itemPenColors.add(new ItemPenColor(GREEN_LIGH));
        itemPenColors.add(new ItemPenColor(GRAY_COLOR));
        itemPenColors.add(new ItemPenColor(RED_COLOR));
        itemPenColors.add(new ItemPenColor(BLUE_COLOR));


        return itemPenColors;
    }
    public static List<ItemPenColor> getBGColors(){
        List<ItemPenColor> itemPenColors = new ArrayList<>();
        itemPenColors.add(new ItemPenColor(YELLOW_COLOR));
        itemPenColors.add(new ItemPenColor(BLACK_COLOR));
        itemPenColors.add(new ItemPenColor(WHITE_COLOR));
        itemPenColors.add(new ItemPenColor(ORANGE_COLOR));
        itemPenColors.add(new ItemPenColor(GRAY_COLOR));
        itemPenColors.add(new ItemPenColor(GREEN_COLOR));
        itemPenColors.add(new ItemPenColor(RED_COLOR));
        itemPenColors.add(new ItemPenColor(BLUE_COLOR));
        itemPenColors.add(new ItemPenColor(GREEN_LIGH));

        return itemPenColors;
    }
}
