package core.node;

import org.apache.commons.lang.StringUtils;

import java.util.*;

public class TagNode extends Node{

    public TagNode parent;
    public String tagName;
    public boolean auto;
    public List<TagNode> nodes = new ArrayList<>();
    public Queue<Node> nodeQueue = new ArrayDeque<>();
    public Map<String, String> attributes = new HashMap<>();
    public TagNode(String tag){
        this.tagName = tag;
    }

    public void add(TextNode node){
        this.nodeQueue.add(node);
    }

    public void add(TagNode node) {
        this.nodes.add(node);
        this.nodeQueue.add(node);
    }

    public void addAttribute(String attr, String value){
        this.attributes.put(attr, value);
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public String print(int i){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[').append(this).append(']');
        String tab = StringUtils.repeat("\t", i);
        for(Node node : nodeQueue){
            stringBuilder.append("\n").append(tab).append("==> ").append(node.print(i + 1));
        }
        return stringBuilder.toString();
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }
}
