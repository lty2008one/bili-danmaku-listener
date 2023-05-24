package cn.ryux.bili.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;

public class GsonConfig {
    private static final Gson instance = builder().create();
    public static Gson instance() {
        return instance;
    }

    public static GsonBuilder builder() {
        return new GsonBuilder()
            .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
            .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE);
    }

}
