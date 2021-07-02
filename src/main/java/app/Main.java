package app;

import node.Element;
import parser.HtmlParser;

public class Main {

    public static void main(String... args) {
        HtmlParser.get("<html class=\"dazdazda\" disabled='testets\"\"' open>body1<span>body2</span></html>").getDocElements().forEach(Element::print);
    }

}