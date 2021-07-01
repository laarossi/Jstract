package parser;

import node.Element;
import document.Document;
import document.DocumentBuilder;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser {

    private final static String OPEN_TAG_PATTERN = "<[^>]*\"";

    private final static String CLOSED_TAG_PATTERN = "</[^>]*\"";

    private final static String ATTRIBUTE_PATTERN = "(\"[^\\\"'/>=]*\")";

    private static Element parent, currentElement;

    private static StringBuilder tagData;

    private static List<Element> parents;

    public static Document get(String data){
        List<Element> documentTags = extractTags(data);
        return DocumentBuilder.create(documentTags).build();
    }

    public static List<Element> extractTags(String data){
        int offset = 0;
        String[] htmlData = data.split("");
        parents = new ArrayList<>();
        parent = null;
        currentElement = null;
        tagData = new StringBuilder();
        StringBuilder dataBuffer = new StringBuilder();
        String character, nextCharacter;
        label:
        while (true) {
            if(offset == htmlData.length) break;
            character = htmlData[offset];
            switch (character) {
                case "<":
                    nextCharacter = offset < htmlData.length - 1 ? htmlData[++offset] : null;
                    if (nextCharacter == null) {
                        dataBuffer.append(character);
                        break label;
                    } else if (nextCharacter.equals("/") && tagData.length() == 0) {
                        clean(dataBuffer);
                        tagData.append(character).append(nextCharacter);
                    } else if (nextCharacter.equals(" ")) {
                        dataBuffer.append(character).append(nextCharacter);
                    } else {
                        clean(dataBuffer, tagData);
                        dataBuffer.append(nextCharacter);
                        tagData.append(character);
                    }
                    break;
                case ">":
                    if (isCloseTag(tagData.toString())) {
                        tagData.append(dataBuffer).append(character);
                        closeTag();
                    }else if (isOpenTag(tagData.toString())) {
                        tagData.append(dataBuffer).append(character);
                        openTag();
                        clean(dataBuffer);
                    } else dataBuffer.append(character);
                    break;
                case "/":
                    nextCharacter = offset < htmlData.length - 1 ? htmlData[++offset] : null;
                    if (nextCharacter == null) {
                        if (isOpenTag(tagData.toString())) {
                            dataBuffer.append(tagData);
                            clean(tagData);
                        }
                        dataBuffer.append(character);
                    } else {
                        if (nextCharacter.equals(">")) {
                            tagData.append(dataBuffer).append(character).append(nextCharacter);
                            openTag();
                            closeTag();
                        } else if (nextCharacter.equals("<") || tagData.length() != 0) {
                            dataBuffer.append(tagData).append(character).append(nextCharacter);
                            clean(tagData);
                        } else {
                            dataBuffer.append(tagData).append(character).append(nextCharacter);
                            clean(tagData);
                        }
                    }
                    break;
                default:
                    dataBuffer.append(character);
                    break;
            }
            offset++;
        }
        return parents;
    }

    public static List<AbstractMap.SimpleEntry<String, String>> extractAttributes(String attributes) {
        List<AbstractMap.SimpleEntry<String,String>> extractedAttributes = new ArrayList<>();
        StringBuilder attribute = new StringBuilder(), attributeData = new StringBuilder();
        String[] attrDataSplit = attributes.replaceAll("[\r\n]", "").replace(" +", " ").split("");
        String character;
        AbstractMap.SimpleEntry<String,String> currentAttr = null;
        int offset = 0;
        while (offset < attrDataSplit.length){
            character = attrDataSplit[offset];
            if(character.equals("=")){
                if(attribute.toString().contains(" ")){
                    String[] attrsData = attribute.toString().split(" ");
                    for(int i = 0; i < attrsData.length; i++){
                        if(i == attrsData.length - 1){
                            attribute.setLength(0);
                            attributeData.append(character);
                            currentAttr = new AbstractMap.SimpleEntry<>(attrsData[i], null);
                            extractedAttributes.add(currentAttr);
                        }else extractedAttributes.add(new AbstractMap.SimpleEntry<String, String>(attrsData[i], "true"));
                    }
                }else{
                    attributeData.append(character);
                    currentAttr = new AbstractMap.SimpleEntry<String, String>(attribute.toString(), null);
                    attribute.setLength(0);
                    extractedAttributes.add(currentAttr);
                }
            }else if(character.matches("[\"']")){
                if(attributeData.length() == 2){
                    String s = attributeData.substring(attributeData.length() - 1, attributeData.length());
                    if(character.equals(s)){
                        if(currentAttr != null){
                            currentAttr.setValue(attribute.toString());
                            clean(attribute, attributeData);
                        }
                    }else attribute.append(character);
                }else if(attributeData.toString().equals("=")){
                    clean(attribute);
                    attributeData.append(character);
                }else{
                    clean(attributeData);
                    attribute.append(character);
                }
            }else{
                attribute.append(character);
                if(offset == attrDataSplit.length - 1){
                    for(String attr : attribute.toString().trim().split(" ")){
                        extractedAttributes.add(new AbstractMap.SimpleEntry<>(attr, "true"));
                    }
                }
            }
            offset++;
        }
        return extractedAttributes;
    }


    public static Element extractElement(String tagData){
        Element element = new Element();
        tagData = tagData.trim().replaceAll(" +", " ").replaceAll("<|>","");
        if(tagData.contains(" ")){
            element.tag = tagData.substring(0, tagData.indexOf(" "));
            List<AbstractMap.SimpleEntry<String, String>> attributes = extractAttributes(tagData.substring(tagData.indexOf(" ")).trim());
            for(AbstractMap.SimpleEntry<String, String> entry : attributes){
                element.setAttribute(entry.getKey(), entry.getValue());
            }
        }else element.tag = tagData;
        return element;
    }

    public static void openTag(){
        Element element = extractElement(tagData.toString());
        if(parent == null){
            parent = element;
            parents.add(parent);
        }else{
            if(currentElement != null) parent = currentElement;
            currentElement = element;
            currentElement.parent = parent;
            parent.addChild(element);
        }
        clean(tagData);
    }

    public static void closeTag(){
        if(currentElement != null){
            if(currentElement.parent.parent == null){
                currentElement = null;
            }else{
                currentElement = currentElement.parent;
                parent = currentElement.parent;
            }
        }else{
            if(parent != null) parent = parent.parent;
        }
        clean(tagData);
    }

    public static Element convert(String data){
        return new Element();
    }

    public static boolean isOpenTag(String data){
        return data.matches("<[^>]*");
    }

    public static boolean isCloseTag(String data){
        return data.matches("</[^>]*");
    }

    public static void clean(StringBuilder... builders){
        for(StringBuilder builder : builders){
            builder.setLength(0);
        }
    }

}
