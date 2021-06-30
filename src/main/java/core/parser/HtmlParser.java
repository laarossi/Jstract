package core.parser;

import core.node.Element;
import core.node.document.Document;
import core.node.document.DocumentBuilder;
import java.util.ArrayList;
import java.util.List;

public class HtmlParser {

    public static Document get(String data){
        List<Element> documentTags = extractTags(data);
        return DocumentBuilder.create(documentTags).build();
    }

    public static List<Element> extractTags(String data){
        int offset = 0;
        List<Element> parents = new ArrayList<>();
        String[] htmlData = data.split("");
        String character, nextCharacter;
        StringBuilder dataBuffer = new StringBuilder(), tagData = new StringBuilder();
        Element parent = null, currentElement = null;
        while (true) {
            if(offset == htmlData.length) break;
            character = htmlData[offset];
            if(character.equals("<")){
                nextCharacter = offset < htmlData.length - 1 ? htmlData[++offset] : null;
                if(nextCharacter == null) {
                    dataBuffer.append(character);
                    break;
                }else if(nextCharacter.equals("/") && tagData.length() == 0){
                    clean(dataBuffer);
                    tagData.append(character).append(nextCharacter);
                }else if(nextCharacter.equals(" ")){
                    dataBuffer.append(character).append(nextCharacter);
                }else{
                    clean(dataBuffer, tagData);
                    dataBuffer.append(nextCharacter);
                    tagData.append(character);
                }
            }else if(character.equals(">")){
                if(isCloseTag(tagData.toString())){
                    tagData.append(dataBuffer).append(character);
                    if(currentElement != null){
                        if(currentElement.parent.parent == null){
                            currentElement = null;
                        }else{
                            currentElement = currentElement.parent;
                            parent = currentElement.parent;
                        }
                    }else{
                        if(parent != null){
                            if(parent.parent == null){
                                parent = null;
                            }else parent = parent.parent;
                        }
                    }
                    clean(tagData);
                }if(isOpenTag(tagData.toString())){
                    tagData.append(dataBuffer).append(character);
                    Element element = new Element(tagData.toString());
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
                }else dataBuffer.append(character);

            }else dataBuffer.append(character);
            offset++;
        }
        return parents;
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
