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

    public String tag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String text() {
        return body;
    }

    public void setBody(String body) {
        if(this.body == null) this.body = body;
        else this.body += body != null ? " " + body : "";
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

    public List<Element> getChildren() {
        return children;
    }

    public void setAttribute(String attribute, String value){
        if(attribute.matches(" *")){
            return;
        }
        String previous = attributes.get(attribute);
        attributes.put(attribute, (previous == null ? value : previous.concat(" ").concat(value)));
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void print(){
        System.out.println("--------------------------------------");
        System.out.println("Tag name : " + tag + ((parent != null) ? " ==> parent : " + parent.tag : " ==> I'm root"));
        System.out.println(body);
        for(String key : attributes.keySet()){
            System.out.print(key + " : '" + attributes.get(key) + "' | ");
        }
        System.out.println();
        for(Element element : children){
            element.print();
        }

    }

    public boolean isEqual(Element comparator) {
        if(tag != null && !tag.trim().isEmpty()){
            if(comparator.tag == null || comparator.tag.trim().isEmpty()) return false;
            if(!tag.trim().equals(comparator.tag.trim())) return false;
        }
        if(attributes.size() > 0){
            if(comparator.attributes.size() == 0) return false;
            Map<String, String> comparatorAttr = comparator.getAttributes();
            for(String key : attributes.keySet()){
                if(!comparatorAttr.containsKey(key)){
                    return false;
                }
                if(!comparatorAttr.get(key).trim().equals(attributes.get(key).trim())){
                    return false;
                }
            }
        }
        return true;
    }

    public String printAttributes(){
        if(attributes.size() == 0) return "none";
        StringBuilder sb = new StringBuilder();
        for(String key : attributes.keySet()){
            sb.append(key).append(":").append(attributes.get(key));
        }
        return sb.toString();
    }

    public String toString(){
        return "Element { " +
                "text : " + text() +
                ", tag : " + tag() +
                ", body : " + body +
                ", attributes : " + printAttributes() +
                ", children : " + children.size() +
                " }";
    }

}
