package enyx.ch.politapp.api;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import enyx.ch.politapp.R;
import enyx.ch.politapp.api.parser.ArticleParser;
import enyx.ch.politapp.model.Article;

/**
 * Created by adrien.manikon on 25.08.15.
 */
public class ArticleApi extends Api {

    public static ArrayList<Article> getFeed() {

        ArticleParser parser = new ArticleParser();

        InputStream in = createInputStream(R.raw.aboutme);

        try {
            return (ArrayList<Article>) parser.parse(in);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
