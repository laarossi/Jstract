package node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElementBuilder {

    private String tag;
    private final Map<String, String> attributes = new HashMap<>();
    private Element parent;
    private final List<Element> children = new ArrayList<>();

    public static ElementBuilder create(String tag){
        ElementBuilder elementBuilder = new ElementBuilder();
        elementBuilder.tag = tag;
        return elementBuilder;
    }

    public ElementBuilder attribute(String attribute, String value){
        attributes.put(attribute, attributes.get(attribute).concat(" ").concat(value).trim());
        return this;
    }

    public ElementBuilder parent(Element parent){
        this.parent = parent;
        return this;
    }

    public ElementBuilder child(Element child){
        this.children.add(child);
        return this;
    }

    public Element build() throws Exception {
        Element element = new Element();
        if(this.tag == null) throw new Exception("Missing tag for html element ");
        element.setTag(this.tag);
        element.setParent(this.parent);
        element.getChildren().addAll(children);
        for(String key : attributes.keySet()){
            element.setAttribute(key, attributes.get(key));
        }
        return element;
    }

}
