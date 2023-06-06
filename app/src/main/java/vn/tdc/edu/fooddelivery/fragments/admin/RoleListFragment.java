package vn.tdc.edu.fooddelivery.fragments.admin;

import android.view.View;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;

public class RoleListFragment extends AbstractFragment implements View.OnClickListener {



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAdd) {
            ((AbstractActivity) getActivity()).setFragment(RoleFormFragment.class, R.id.frameLayout, true);
        }
    }
}
