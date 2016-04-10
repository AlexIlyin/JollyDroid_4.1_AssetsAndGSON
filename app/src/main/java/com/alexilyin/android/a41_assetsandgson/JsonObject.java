package com.alexilyin.android.a41_assetsandgson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 06.04.16.
 */
class JsonObject {

    @SerializedName("_id")
    public String jsonId;

    @SerializedName("title")
    public String jsonTitle;

    @SerializedName("filename")
    public String jsonFilename;
}
