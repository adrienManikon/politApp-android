package enyx.ch.politapp.api.mapping;

import java.util.LinkedHashMap;

import enyx.ch.politapp.helper.InstanceTypePair;
import enyx.ch.politapp.helper.TypeTag;
import enyx.ch.politapp.model.Article;

/**
 * Created by adrien.manikon on 25.08.15.
 */
public class ArticleMapping {

    public static String BIG_IMAGE_URL = "bigimageurl";
    public static String SMALL_IMAGE_URL = "smallimageurl";
    public static String TITLE_NEWS = "titlenews";
    public static String SHORT_DESCRIPTION = "shortdescription";

    private LinkedHashMap<String, InstanceTypePair> linkedHashMap;

    public ArticleMapping() {

        linkedHashMap = new LinkedHashMap<>();

        linkedHashMap.put(BIG_IMAGE_URL, new InstanceTypePair(null, TypeTag.STRING));
        linkedHashMap.put(SMALL_IMAGE_URL, new InstanceTypePair(null, TypeTag.STRING));
        linkedHashMap.put(TITLE_NEWS, new InstanceTypePair(null, TypeTag.STRING));
        linkedHashMap.put(SHORT_DESCRIPTION, new InstanceTypePair(null, TypeTag.STRING));

    }

    public Article buildObject() {

        Article article = new Article();

        article.setBigImageUrl(linkedHashMap.get(BIG_IMAGE_URL).getInstance());
        article.setSmallImageUrl(linkedHashMap.get(SMALL_IMAGE_URL).getInstance());
        article.setTitleNews(linkedHashMap.get(TITLE_NEWS).getInstance());
        article.setShortDescription(linkedHashMap.get(SHORT_DESCRIPTION).getInstance());

        return article;
    }

    public LinkedHashMap<String, InstanceTypePair> getLinkedHashMap() {
        return linkedHashMap;
    }

    public void setLinkedHashMap(LinkedHashMap<String, InstanceTypePair> linkedHashMap) {
        this.linkedHashMap = linkedHashMap;
    }

    public TypeTag getTypeTag(String name) {
        if (containsTagName(name))
            return linkedHashMap.get(name).getType();
        else
            return TypeTag.NULL;
    }

    public void setInstance(String tagName, String instance) {

        linkedHashMap.get(tagName).setInstance(instance);

    }

    public boolean containsTagName(String name) {
        return linkedHashMap.containsKey(name);
    }
}
