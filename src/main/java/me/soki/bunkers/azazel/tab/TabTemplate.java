package me.soki.bunkers.azazel.tab;


import java.util.ArrayList;
import java.util.List;

public class TabTemplate {

    private final List<String> left;
    private final List<String> middle;
    private final List<String> right;
    private final List<String> farRight;

    public List<String> getLeft() {
        return left;
    }

    public List<String> getMiddle() {
        return middle;
    }

    public List<String> getRight() {
        return right;
    }

    public List<String> getFarRight() {
        return farRight;
    }

    public TabTemplate() {
        left = new ArrayList<>();
        middle = new ArrayList<>();
        right = new ArrayList<>();
        farRight = new ArrayList<>();
    }

    /*
        Will only display on 1.8 clients
     */
    public TabTemplate farRight(String string) {
        return farRight(farRight.size(), string);
    }

    public TabTemplate farRight(int index, String string) {
        if (index > farRight.size()) {
            for (int i = farRight.size(); i < index; i++) {
                farRight.add("");
            }
        }
        farRight.add(index, string);
        return this;
    }

    public TabTemplate left(String string) {
        return left(left.size(), string);
    }

    public TabTemplate middle(String string) {
        return middle(middle.size(), string);
    }

    public TabTemplate right(String string) {
        return right(right.size(), string);
    }

    public TabTemplate left(int index, String string) {
        if (index > left.size()) {
            for (int i = left.size(); i < index; i++) {
                left.add("");
            }
        }
        left.add(index, string);
        return this;
    }

    public TabTemplate middle(int index, String string) {
        if (index > middle.size()) {
            for (int i = middle.size(); i < index; i++) {
                middle.add("");
            }
        }
        middle.add(index, string);
        return this;
    }

    public TabTemplate right(int index, String string) {
        if (index > right.size()) {
            for (int i = right.size(); i < index; i++) {
                right.add("");
            }
        }
        right.add(index, string);
        return this;
    }

}
