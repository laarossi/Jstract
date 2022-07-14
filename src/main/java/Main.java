import core.node.TagNode;
import core.parser.Parser;
import doc.Document;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        Document document = new Parser("<a href='www.google.com'><div>dzadadzdzdazd<p>dzdazdaz<a href=\"da\">dazdazd</a></p>zdazdazdazd</div><header><a></a> <yousef</header></a>").parse();
        List<TagNode> node = document.get("a");
        System.out.println(node.size() + " " + node.get(0).tagName);
    }

}
