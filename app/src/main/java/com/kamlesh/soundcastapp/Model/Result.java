
package com.kamlesh.soundcastapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("music_file")
    @Expose
    private Music_file music_file;
    @SerializedName("thumbnail_file")
    @Expose
    private Thumbnail_file thumbnail_file;
    @SerializedName("ACL")
    @Expose
    private ACL aCL;

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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Music_file getMusic_file() {
        return music_file;
    }

    public void setMusic_file(Music_file music_file) {
        this.music_file = music_file;
    }

    public Thumbnail_file getThumbnail_file() {
        return thumbnail_file;
    }

    public void setThumbnail_file(Thumbnail_file thumbnail_file) {
        this.thumbnail_file = thumbnail_file;
    }

    public ACL getACL() {
        return aCL;
    }

    public void setACL(ACL aCL) {
        this.aCL = aCL;
    }

}
