package enyx.ch.politapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import enyx.ch.politapp.PolitApplication;
import enyx.ch.politapp.R;
import enyx.ch.politapp.model.Article;

/**
 * Created by adrien.manikon on 17.07.15.
 */
public class ListImageTextAdapter extends BaseAdapter {

    private Context context;
    private List<ArticleItemAdapter> articleItemAdapters;
    private int resId;

    public ListImageTextAdapter(Context context, int resId, ArrayList<Article> articles) {
        initFields(context, resId, articles);
    }

    private void initFields(Context context, int resId, ArrayList<Article> articles) {

        this.context = context;
        this.resId = resId;

        articleItemAdapters = new ArrayList<>();

        for (Article article : articles) {

            articleItemAdapters.add(new ArticleItemAdapter(article));

        }

    }

    @Override
    public int getCount() {
        return articleItemAdapters.size();
    }

    @Override
    public Object getItem(int position) {
        return articleItemAdapters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        ArticleItemAdapter articleItem = (ArticleItemAdapter) getItem(position);
        View viewToUse;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            viewToUse = inflater.inflate(resId, null);

            viewHolder = new ViewHolder();
            viewHolder.smallImage = (ImageView) viewToUse.findViewById(R.id.small_image);
            viewHolder.title = (TextView) viewToUse.findViewById(R.id.title);
            viewHolder.shortDescription = (TextView) viewToUse.findViewById(R.id.short_description);

            viewToUse.setTag(viewHolder);


        } else {

            viewToUse = convertView;
            viewHolder = (ViewHolder) viewToUse.getTag();

        }

        viewHolder.title.setText(articleItem.getTitle());
        viewHolder.shortDescription.setText(articleItem.getShortDescription());
        viewHolder.smallImage.setImageResource(PolitApplication.getImageResIdByName(articleItem.getSmallImageUrl()));

        return viewToUse;
    }

    private class ViewHolder {
        ImageView smallImage;
        TextView title;
        TextView shortDescription;
    }
}
