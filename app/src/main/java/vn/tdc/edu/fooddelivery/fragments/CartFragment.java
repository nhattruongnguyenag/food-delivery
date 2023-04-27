package vn.tdc.edu.fooddelivery.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.tdc.edu.fooddelivery.R;

public class CartFragment extends AbstractFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.fragment_cart, container, false);
        return fragmentLayout;
    }
}