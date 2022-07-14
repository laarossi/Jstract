package doc;

import core.node.Node;
import core.node.TagNode;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class Document {

    public List<TagNode> rootNodes = new ArrayList<>();
    public Document(){}

    public void add(TagNode node) {
        this.rootNodes.add(node);
    }

    public String toString(){
        StringBuilder output = new StringBuilder();
        rootNodes.forEach(n -> {
            output.append(n.print(1));
        });
        return output.toString();
    }

    public List<TagNode> get(String element) {
        element = element.trim();
        if(element.isEmpty()) return null;
        String[] elements = element.replaceAll(" +", " ").trim().split(" ");
        List<TagNode> nodesFound = deepSearch(elements[0], rootNodes);
        if(elements.length == 1) return nodesFound;
        for(int i = 0; i < elements.length - 1; i++){
            nodesFound = search(elements[i], nodesFound);
            if(nodesFound.size() == 0) return nodesFound;
        }

        return nodesFound;
    }

    public List<TagNode> search(String element, List<TagNode> nodes){
        List<TagNode> tagNodes = new ArrayList<>();
        for(TagNode node : nodes){
            if(node.tagName.equals(element))
                tagNodes.addAll(node.nodes);
        }

        return tagNodes;
    }

    public List<TagNode> deepSearch(String element, List<TagNode> nodes){
        List<TagNode> nodesFound = new ArrayList<>();
        Queue<TagNode> nodesQueue = new ArrayDeque<>(nodes);
        TagNode currentNode;
        while ((currentNode = nodesQueue.poll()) != null){
            if(currentNode.tagName.equals(element))
                nodesFound.add(currentNode);
            nodesQueue.addAll(currentNode.nodes);
        }
        return nodesFound;

    }

    public void print(){

    }

}
