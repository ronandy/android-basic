package com.ronandy.getTweets;

/**
 * Created by ronandy on 10/17/15.
 */
public class Tweet {

    private String created_at;
    private String screen_name;
    private String name;
    private String text;

    public Tweet(String created_at, String screen_name, String name, String text) {
        this.created_at = created_at;
        this.name = name;
        this.screen_name = screen_name;
        this.text = text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {

        String formato = "Tweets{\n" +
                "FECHA=" + created_at +
                "\nUSUARIO=" + screen_name +
                "\nNOMBRE=" + name +
                "\nTEXTO=" + text +
                "\n}";
        return formato;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
