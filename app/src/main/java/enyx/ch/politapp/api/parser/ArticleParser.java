package enyx.ch.politapp.api.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import enyx.ch.politapp.api.mapping.ArticleMapping;

/**
 * Created by adrien.manikon on 24.08.15.
 */
public class ArticleParser extends ParentParser {

    public static final String FEED_TAG = "articles";
    public static final String ENTITY_TAG = "article";

    @Override
    protected String getEntityTag() {
        return ENTITY_TAG;
    }

    @Override
    protected String getFeedTag() {
        return FEED_TAG;
    }

    @Override
    protected Object readEntry(XmlPullParser parser) throws IOException, XmlPullParserException {

        parser.require(XmlPullParser.START_TAG, ns, ENTITY_TAG);

        ArticleMapping articleMapping = new ArticleMapping();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch (articleMapping.getTypeTag(name)) {

                case STRING:

                    if (articleMapping.containsTagName(name)) {
                        articleMapping.setInstance(name, readStringTag(parser, name));
                    } else {
                        skip(parser);
                    }

                    break;
                default:
                    skip(parser);
                    break;

            }

        }

        return articleMapping.buildObject();
    }

}
