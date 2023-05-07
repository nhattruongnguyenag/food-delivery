package vn.tdc.edu.fooddelivery.components;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import vn.tdc.edu.fooddelivery.models.BaseModel;

public class MultiSelectDialog<T extends BaseModel> extends AlertDialog.Builder {

    private List<T> listObjects;
    private List<Integer> listSelectedObjectsId = new ArrayList<Integer>();
    private Action onActionClickListener;

    public void setOnActionClickListener(Action actionClickListener) {
        this.onActionClickListener = actionClickListener;
    }

    public MultiSelectDialog(@NonNull Context context, List<T> listObjects) {
        super(context);
        this.listObjects = listObjects;
        init();
    }

    public MultiSelectDialog(@NonNull Context context, int themeResId, List<T> listObjects) {
        super(context, themeResId);
        this.listObjects = listObjects;
        init();
    }

    private void init() {
        List<CharSequence> options = listObjects.stream().map(option -> option.toString()).collect(Collectors.toList());
        boolean[] checkedItems = new boolean[listObjects.size()];

        for (int i = 0; i < checkedItems.length; i++) {
            checkedItems[i] = true;
        }

        setMultiChoiceItems(options.toArray(new CharSequence[options.size()]), checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    Log.d("categories", "add " + listObjects.get(i).getId());
                    listSelectedObjectsId.add(listObjects.get(i).getId());
                } else {
                    Log.d("categories", "remove " + listObjects.get(i).getId());
                    listSelectedObjectsId.remove(listObjects.get(i));
                }
            }
        });

        setCancelable(false);

        setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onActionClickListener.ok(listSelectedObjectsId);
                dialogInterface.dismiss();
            }
        });
    }

    public interface Action {
        void cancel();
        void ok(List<Integer> listObjectSelectedId);
    }
}
