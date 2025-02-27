package utobe.learn2code.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

abstract class SmartFragmentStatePagerAdapter extends FragmentStatePagerAdapter implements IAbstractAdapter {
    // Sparse array to keep track of registered fragments in memory
    private final SparseArray<Fragment> registeredFragments = new SparseArray<>();

    SmartFragmentStatePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Register the fragment when the item is instantiated
    @NonNull
    @Override
    public Fragment instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    // Unregister when the item is inactive
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    // Returns the fragment for the position (if instantiated)
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public Fragment fetchFragmentByPosition(ViewGroup pagerInstance, int position) {
        Fragment existingInstance = registeredFragments.get(position);
        if (existingInstance != null) {
            return existingInstance;
        } else {
            return instantiateItem(pagerInstance, position);
        }
    }

}
