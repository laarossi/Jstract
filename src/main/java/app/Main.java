package app;

import node.Element;
import parser.HtmlParser;

public class Main {

    public static void main(String... args) {
        HtmlParser.get("<html class=\"dazdazda\" disabled='testets\"\"' open><body></body><p style=\"color:red; font-size:16px;\">").docElements.forEach(Element::print);
    }

}