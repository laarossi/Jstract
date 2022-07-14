package core.parser;

import core.node.Node;
import core.node.TagNode;
import core.node.TextNode;
import doc.Document;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    String inputBuffer;
    int offset;
    private static final String OPEN_TAG_EXPR = "(?<html>(<(?<tag>([a-z]*))(?<data>([^<>/]*))(?<end>(/?))>))";
    private static final String CLOSE_TAG_EXPR = "</(?<tag>([a-z]*))>";

    public Parser(String inputBuffer){
        this.inputBuffer = inputBuffer;
    }

    public void init(){
        inputBuffer = null;
        offset = 0;
    }

    public Document parse() throws Exception {
        String buffer;
        Document doc = new Document();
        TagNode currentNode = null;
        Matcher matcher;
        ArrayDeque<TagNode> tagNodesQueue = new ArrayDeque<>();
        while((buffer = read()) != null){
            matcher = Pattern.compile(OPEN_TAG_EXPR).matcher(buffer);
            if(matcher.find()){
                TagNode node = createTag(matcher.group("tag"), matcher.group("data"), matcher.group("end") == null || matcher.group("end").isEmpty());
                tagNodesQueue.push(node);
                if(currentNode == null){
                    currentNode = node;
                    doc.add(currentNode);
                }else{
                    node.parent = currentNode;
                    currentNode.add(node);
                    currentNode = node;
                }
                continue;
            }

            matcher = Pattern.compile(CLOSE_TAG_EXPR).matcher(buffer);
            if(matcher.find()){
                TagNode tag = tagNodesQueue.pop();
                if(!matcher.group("tag").equals(tag.tagName)) throw new Exception("");
                currentNode = tag.parent;
                continue;
            }

            TextNode textNode = new TextNode(buffer);
            if(currentNode == null) continue;
            currentNode.add(textNode);
        }

        return doc;
    }

    public TagNode createTag(String tagName, String metaData, boolean auto){
        TagNode node = new TagNode(tagName);
        node.setAuto(auto);
        Matcher matcher = Pattern.compile("(?<attr>([a-zA-Z])*)(=([\"'])?(?<value>[^\\\\4]*)\\4)?").matcher(metaData.trim());
        HashMap<String, String> attributes = new HashMap<>();
        while (matcher.find()){
            String attr = matcher.group("attr"), attrValue = matcher.group("value");
            if(attr == null || attr.trim().isEmpty() || attrValue == null || attrValue.trim().isEmpty() ) continue;
            attributes.put(attr, attrValue);
        }
        node.setAttributes(attributes);
        return node;
    }

    public String read(){
        StringBuilder buffer = new StringBuilder();
        long bufferSize = this.inputBuffer.length();
        while (this.offset < bufferSize - 1){
            char currentChar = this.inputBuffer.charAt(offset), next;
            if(offset == bufferSize - 1 || currentChar != '<' || this.inputBuffer.charAt(offset + 1) == ' '){
                buffer.append(currentChar);
                offset++;
                continue;
            }

            if(buffer.length() > 0){
                return buffer.toString();
            }

            next = this.inputBuffer.charAt(++offset);
            buffer.append(currentChar).append(next);
            while(currentChar != '>' && offset < bufferSize){
                currentChar = this.inputBuffer.charAt(++offset);
                buffer.append(currentChar);
            }
            offset++;
            return buffer.toString();
        }

        return buffer.length() == 0 ? null : buffer.toString();
    }

}
