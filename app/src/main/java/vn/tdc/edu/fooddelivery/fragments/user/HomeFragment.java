package vn.tdc.edu.fooddelivery.fragments.user;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;

public class HomeFragment extends AbstractFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.fragment_home, container, false);
        return fragmentLayout;
    }
}