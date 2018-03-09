package me.mundane.lazyloadfragmentdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by mundane on 2018/3/7 下午8:11
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> mFragmentList;
    public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 0";
            case 1:
                return "SECTION 1";
            case 2:
                return "SECTION 2";
            case 3:
                return "SECTION 3";
        }
        return null;
    }
}
