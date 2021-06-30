package app;

import core.parser.HtmlParser;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String... args) {
        HtmlParser.get("<html class=\"dazda\"><body><span style=\"background-red:red;\"></span>dzadazdazdazdazd<p><i>dazdazdzda</i></p>dzadazdazd</html>");
    }

}