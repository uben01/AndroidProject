package utobe.learn2code.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import utobe.learn2code.fragment.PageFragment;
import utobe.learn2code.model.Page;

public class TopicAdapter extends SmartFragmentStatePagerAdapter {
    private final ArrayList<Page> pages;
    private final boolean isTest;
    private final ViewPager vPager;

    public TopicAdapter(FragmentManager fm, ArrayList<Page> pages, boolean isTest, ViewPager mViewPager) {
        super(fm);
        this.pages = pages;
        this.isTest = isTest;
        this.vPager = mViewPager;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(pages.get(position).getId(), isTest);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).getTitle();
    }
}
