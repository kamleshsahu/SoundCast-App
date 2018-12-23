package com.kamlesh.soundcastapp.Model.UploadModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("error")
    @Expose
    private String error;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
