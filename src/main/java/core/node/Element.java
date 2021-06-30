package core.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Element {

    public Element parent;
    public List<Element> children = new ArrayList<>();
    public Map<String,String> attributes = new HashMap<>();
    public String tag;
    public String body;

    public Element(String tag){
        this.tag = tag;
    }

    public void addChild(Element element){
        this.children.add(element);
    }

    public void print() {
        System.out.println(tag + " parent : " + parent.tag);
        for(Element e : children){
            e.print();
        }
    }

    public void setAttribute(String s) {
        Pattern pattern = Pattern.compile("(?<prop>[^>])*=(\"|')\\s*(?<value>[^>]*)\\s*(\"|')");
        Matcher matcher = pattern.matcher(s);
        matcher.replaceAll("(?<prop>[^>])*=")
    }
}
