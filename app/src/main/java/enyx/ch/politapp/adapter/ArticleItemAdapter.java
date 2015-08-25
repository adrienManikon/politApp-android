package enyx.ch.politapp.adapter;

import enyx.ch.politapp.model.Article;

/**
 * Created by adrien.manikon on 25.08.15.
 */
public class ArticleItemAdapter {

    private Article article;

    public ArticleItemAdapter(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getTitle() {
        return article.getTitleNews();
    }

    public String getShortDescription() {
        return article.getShortDescription();
    }

    public String getSmallImageUrl() {
        return article.getSmallImageUrl();
    }
}
