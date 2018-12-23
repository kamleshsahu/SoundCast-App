
package com.kamlesh.soundcastapp.Model.UploadModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Song {

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

}
