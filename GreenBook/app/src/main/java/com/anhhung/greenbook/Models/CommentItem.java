package com.anhhung.greenbook.Models;

public class CommentItem  {
    String Title, Content, Date;
    int Avatar;

    public CommentItem() {
    }

    public CommentItem(String title, String content, String date, int avatar) {
        Title = title;
        Content = content;
        Date = date;
        Avatar = avatar;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getAvatar() {
        return Avatar;
    }

    public void setAvatar(int avatar) {
        Avatar = avatar;
    }
}
