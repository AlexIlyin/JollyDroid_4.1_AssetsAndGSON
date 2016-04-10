package com.alexilyin.android.a41_assetsandgson;

import android.content.Context;

import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by user on 05.04.16.
 */
public class AssetsLoader {

    private static final String JSON_ASSETS = "index.json";

    Context context;


    public AssetsLoader(Context context) {
        this.context = context;
    }


    public void loadToCacheDB() {

        context.deleteDatabase(CacheDBContract.CACHE_DB);

        String jsonString = loadAssetsStringFile(JSON_ASSETS);

        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonObject[] jsonObjects = gsonBuilder.create().fromJson(jsonString, JsonObject[].class);

        for (JsonObject o : jsonObjects) {
            CacheDBContract.Asset.insert(context, o);
        }


    }

    private String loadAssetsStringFile(String filename) {
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            context.getAssets().open(filename)
                    )
            );

            String s;
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return stringBuffer.toString();
    }

}
