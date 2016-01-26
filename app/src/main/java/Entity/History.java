package Entity;

import android.text.Html;

public class History {
    private String title;
    private String content;
    private String date;
    private float progress;

    public History(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return Html.fromHtml(title).toString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Html.fromHtml(content).toString();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return Html.fromHtml(date).toString();
    }

    public void setDate(String date) {
        this.date = date;
    }
}