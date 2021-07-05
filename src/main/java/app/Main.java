package app;

import document.Document;
import node.Element;
import parser.HtmlParser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String... args) throws IOException {
        Document document = HtmlParser.get(new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("index.html")).getPath()));
        List<Element> elements = document.getDocElements();
    }

}