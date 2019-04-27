package com.haixi.spacetime.DynamicModel.Entity;

import java.util.List;

public class Dynamic {
    public List<String> tags;
    public int imageId;
    public String name;
    private int userId = 0;
    public String content;

    public Dynamic(int userId){
        this.userId = userId;
    }

    public int getUserId(){
        return userId;
    }

    public boolean isTag(String tag){
        if (tag.equals("全部") || tag.equals("")) return true;
        for (String tagContent: tags){
            if (tag.equals(tagContent))
                return true;
        }
        return false;
    }
}
