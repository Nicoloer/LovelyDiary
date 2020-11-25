package com.example.lovelydiary;

/**对应diary表的实体类*/
public class Diary {
    private Integer id;
    private String title;
    private String content;
    private String date;
    private String author;
    private String image;
    public Diary() {
    }

    public Diary(int id,String title, String content, String date, String author,String image) {
        this.id=id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.author = author;
        this.image=image;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
