package com.anhhung.greenbook.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.anhhung.greenbook.R;
import com.anhhung.greenbook.Models.IntroItemModel;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {
    Context mContext ;
    List<IntroItemModel> mListScreen;

    public IntroViewPagerAdapter(Context mContext, List<IntroItemModel> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_intro_screen,null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.imgIntroPicture);
        TextView title = layoutScreen.findViewById(R.id.txtIntroTitle);
        TextView description = layoutScreen.findViewById(R.id.txtIntroDescription);

        title.setText(mListScreen.get(position).getTitle());
        description.setText(mListScreen.get(position).getDescription());
        imgSlide.setImageResource(mListScreen.get(position).getScreenImg());

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);

    }
}
