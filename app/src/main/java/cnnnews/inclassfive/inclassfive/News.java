package cnnnews.inclassfive.inclassfive;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ojasv on 2/13/17.
 */
public class News implements Serializable,  Comparable<News>{
    private String title;
    private String newsDescription;
    private String newsFeedLink;
    private Date pubDate;
    private String thumbnailImageUrl;

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    String largeImageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getNewsFeedLink() {
        return newsFeedLink;
    }

    public void setNewsFeedLink(String newsFeedLink) {
        this.newsFeedLink = newsFeedLink;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    @Override
    public int compareTo(News another) {
        return getPubDate().compareTo(another.getPubDate());
    }
}
