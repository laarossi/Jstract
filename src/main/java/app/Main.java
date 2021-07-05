package app;

import document.Document;
import node.Element;
import parser.HtmlParser;

public class Main {

    public static void main(String... args) {
        HtmlParser.get("<html><div> <p> <span>2</span> </p> <p class='er'>dzadazd  <span>1</span> </p> </div></html>").get("div p.er").forEach(System.out::println);
    }

}