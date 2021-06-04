package com.meetkiki.conrrent.deepcopy;

import com.google.gson.Gson;

public class GsonCopy {

    public static Object copy(Object source) {
        return new Gson().fromJson(new Gson().toJson(source), source.getClass());
    }


}
