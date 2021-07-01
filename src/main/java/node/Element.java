package node;

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

    public Element() { }

    public Element(String tag){
        this.tag = tag;
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

    public void print(){

        System.out.println("Tag name : " + tag + ((parent != null) ? " ==> parent : " + parent.tag : " ==> I'm root"));
        for(String key : attributes.keySet()){
            System.out.print(key + " : '" + attributes.get(key) + "' | ");
        }
        System.out.println();
        for(Element element : children){
            element.print();
        }

    }

}
