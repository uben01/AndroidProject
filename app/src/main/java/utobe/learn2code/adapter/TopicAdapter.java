package utobe.learn2code.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

import utobe.learn2code.fragment.PageFragment;
import utobe.learn2code.model.Page;

public class TopicAdapter extends SmartFragmentStatePagerAdapter {
    ArrayList<Page> pages;

    public TopicAdapter(FragmentManager fm, ArrayList<Page> pages) {
        super(fm);
        this.pages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1, pages.get(position).getId());
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
