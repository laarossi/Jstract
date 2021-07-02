package node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Element {

    private Element parent;
    private List<Element> children = new ArrayList<>();
    private Map<String,String> attributes = new HashMap<>();
    private String tag;
    private String body;

    public Element() { }

    public Element(String tag){
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Element getParent() {
        return parent;
    }

    public void setParent(Element parent) {
        this.parent = parent;
    }

    public void addChild(Element element){
        this.children.add(element);
    }

    public void setAttribute(String attribute, String value){
        if(attribute.matches(" *")){
            return;
        }
        String previous = attributes.get(attribute);
        attributes.put(attribute, (previous == null ? value : previous.concat(" ").concat(value)));
    }

    public String text(){
        return null;
    }

    public void print(){
        System.out.println("--------------------------------------");
        System.out.println("Tag name : " + tag + ((parent != null) ? " ==> parent : " + parent.tag : " ==> I'm root"));
        System.out.println(body.length());
        for(String key : attributes.keySet()){
            System.out.print(key + " : '" + attributes.get(key) + "' | ");
        }
        System.out.println();
        for(Element element : children){
            element.print();
        }

    }

}
