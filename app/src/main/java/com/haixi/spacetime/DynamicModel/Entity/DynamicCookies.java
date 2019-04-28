package com.haixi.spacetime.DynamicModel.Entity;

import com.haixi.spacetime.R;

import java.util.ArrayList;
import java.util.List;

public class DynamicCookies {
    public static List<Dynamic> circleDynamics = new ArrayList<Dynamic>();
    public static List<Dynamic> followDynamics = new ArrayList<Dynamic>();
    public static List<String> tags;
    public static String currentTag = "全部";
    public static int currentUserId;

    private static boolean isReset = false;

    public static void initData(){
        if (isReset){
            return;
        }
        isReset = true;

        String s1 = "吃鸡党", s2 = "大社联", s3 = "俱乐部", s4 = "潮牌", s5 = "王者荣耀";
        Dynamic dynamic1 = new Dynamic(1);
        dynamic1.imageId = R.drawable.jack;
        dynamic1.name = "Jack";
        dynamic1.content = "过放荡不羁的生活，容易得像顺水推舟，但是要结识良朋益友" +
                "，却难如登天。 —— 巴尔扎克";
        dynamic1.tags = new ArrayList<>();
        dynamic1.tags.add(s1);
        dynamic1.tags.add(s2);
        circleDynamics.add(dynamic1);

        Dynamic dynamic2 = new Dynamic(2);
        dynamic2.imageId = R.drawable.william;
        dynamic2.name = "William";
        dynamic2.content = "每当我为世界的现状感到沮丧时，我就会想到伦敦希思罗机场的接机大厅。";
        dynamic2.tags = new ArrayList<>();
        dynamic2.tags.add(s1);
        dynamic2.tags.add(s3);
        circleDynamics.add(dynamic2);

        Dynamic dynamic3 = new Dynamic(3);
        dynamic3.imageId = R.drawable.daniel;
        dynamic3.name = "Daniel";
        dynamic3.content = "真理惟一可靠的标准就是永远自相符合。";
        dynamic3.tags = new ArrayList<>();
        dynamic3.tags.add(s1);
        dynamic3.tags.add(s4);
        circleDynamics.add(dynamic3);

        Dynamic dynamic4 = new Dynamic(4);
        dynamic4.imageId = R.drawable.jack;
        dynamic4.name = "Jack";
        dynamic4.content = "土地是以它的肥沃和收获而被估价的；才能也是土地，不过它生产的不" +
                "是粮食，而是真理。如果只能滋生瞑想和幻想的话，即使再大的才能也只是砂地或盐池，" +
                "那上面连小草也长不出来的。 —— 别林斯基";
        dynamic4.tags = new ArrayList<>();
        dynamic4.tags.add(s2);
        dynamic4.tags.add(s3);
        circleDynamics.add(dynamic4);

        Dynamic dynamic5 = new Dynamic(5);
        dynamic5.imageId = R.drawable.william;
        dynamic5.name = "William";
        dynamic5.content = "我需要三件东西：爱情友谊和图书。然而这三者之间何其相通！炽热的爱情" +
                "可以充实图书的内容，图书又是人们最忠实的朋友。 —— 蒙田";
        dynamic5.tags = new ArrayList<>();
        dynamic5.tags.add(s2);
        dynamic5.tags.add(s4);
        circleDynamics.add(dynamic5);

        Dynamic dynamic6 = new Dynamic(6);
        dynamic6.imageId = R.drawable.daniel;
        dynamic6.name = "Daniel";
        dynamic6.content = "世界上一成不变的东西，只有“任何事物都是在不断变化的”" +
                "这条真理。 —— 斯里兰卡";
        dynamic6.tags = new ArrayList<>();
        dynamic6.tags.add(s3);
        dynamic6.tags.add(s4);
        circleDynamics.add(dynamic6);

        refreshTag();
    }

    public static boolean isExistTag(String tag){
        for (String tag1: tags){
            if (tag.equals(tag1)){
                return true;//存在
            }
        }
        return false;
    }

    public static void refreshTag(){
        tags = new ArrayList<String>();
        tags.add("全部");
        for (Dynamic dynamic: DynamicCookies.circleDynamics){
            for (String s: dynamic.tags){
                if (isExistTag(s)){
                    continue;
                }else {
                    tags.add(s);
                }
            }
        }
    }
}
