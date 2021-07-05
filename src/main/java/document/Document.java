package document;

import node.Element;
import node.Tag;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Document {

    private CopyOnWriteArrayList<Element> docElements = new CopyOnWriteArrayList<>();
    private String title;
    private Element root;
    private CopyOnWriteArrayList<Element> searchList = new CopyOnWriteArrayList<>();

    public void init(List<Element> docElements) {
        setDocElements(docElements);
        if (docElements.size() == 1) root = docElements.get(0);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Element> getDocElements() {
        return docElements;
    }

    public void setDocElements(List<Element> elements) {
        this.docElements = new CopyOnWriteArrayList<>(elements);
    }

     public List<Element> get(String pattern) {
        if (pattern == null || pattern.trim().isEmpty()) return null;
        return search(pattern);
    }

    public List<List<Element>> get(String... patterns){
        List<List<Element>> result = new ArrayList<>();
        for(String pattern : patterns){
            result.add(search(pattern));
        }
        return result;
    }

    public List<Element> get(Tag... tags){
        StringBuilder pattern = new StringBuilder();
        for(Tag tag : tags){
            pattern.append(tag.toString()).append(" ");
        }
        return search(pattern.toString());
    }

    private List<Element> search(String pattern) {
        String[] patterns = pattern.trim().replaceAll("([^/\"'><]*)(\\.|#|\\[)", "$1<=>$2").replaceAll(" +", " ").split(" ");
        List<Element> comparators = new ArrayList<>();
        searchList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < patterns.length; i++) {
            String p = patterns[i];
            List<String> subPatterns = Arrays.stream(p.split("<=>")).filter(value -> !value.trim().isEmpty()).collect(Collectors.toList());
            Element comparator = new Element();
            for (String spec : subPatterns) {
                if (spec.startsWith(".") || spec.startsWith("#")) {
                    if (spec.trim().startsWith(".")) {
                        comparator.setAttribute("class", spec.replace(".", ""));
                    } else comparator.setAttribute("id", spec.replace("#", ""));
                } else {
                    Pattern attrPattern = Pattern.compile("\\[(?<attr>[^=\\\"'\\/><]+)(=(?<value>.*)){0,1}\\]");
                    Matcher matcher = attrPattern.matcher(spec);
                    if (matcher.find()) {
                        String value = (matcher.group("value") != null && !matcher.group("value").trim().isEmpty()) ? matcher.group("value") : "true";
                        comparator.setAttribute(matcher.group("attr"), value);
                    } else comparator.setTag(spec);
                }
            }
            comparators.add(comparator);
        }
        for(Element comparator : comparators){
            for(Element parent : searchList.size() == 0 ? docElements : searchList){
                searchNode(comparator, parent);
            }
        }
        return searchList;
    }

    public void searchNode(Element comparator, Element element) {
        if(comparator.isEqual(element)){
            if(!searchList.contains(element)) searchList.add(element);
            else searchList.remove(element);
        }else{
            searchList.remove(element);
        }
        for(Element child : element.getChildren()){
            searchNode(comparator, child);
        }
    }

}
