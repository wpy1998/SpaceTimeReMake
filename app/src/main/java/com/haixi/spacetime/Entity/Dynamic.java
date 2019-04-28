package com.haixi.spacetime.Entity;

import java.util.List;

public class Dynamic {
    public List<String> tags;
    public int imageId;
    public String name;
    public User user;
    public String content;

    public Dynamic(int userId){
        if (user == null){
            user = new User();
            user.userId = -1;
        }
        this.user.userId = userId;
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
