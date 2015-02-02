package com.yahoo.imagesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by rakeshch on 1/31/15.
 */
public class SettingDialogFragment  extends DialogFragment {


    public static SettingDialogFragment newInstance(String title) {
        SettingDialogFragment dialogFragment = new SettingDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString("title", title);
        dialogFragment.setArguments(arguments);
        return dialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        SearchParam searchParam = (SearchParam) getArguments().getSerializable("Settings");

        View view = inflater.inflate(R.layout.activity_settings, container);

        final Spinner imageSizeSpinner = (Spinner) view.findViewById(R.id.spinnerImageSize);
        String[] imageSizes = ImageSearchConstants.imageSizes.clone();
        imageSizes[0] = "any";
        ArrayAdapter imageSizeAdapter = new ArrayAdapter(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item, imageSizes);
        imageSizeSpinner.setAdapter(imageSizeAdapter);
        imageSizeSpinner.setSelection(searchParam.imageSizePosition);

        final Spinner colorFilterSpinner = (Spinner) view.findViewById(R.id.spinnerColorFilter);
        String[] colorFilters = ImageSearchConstants.colorFilters.clone();
        colorFilters[0] = "any";
        ArrayAdapter colorFilterAdapter = new ArrayAdapter(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item, colorFilters);
        colorFilterSpinner.setAdapter(colorFilterAdapter);
        colorFilterSpinner.setSelection(searchParam.colorFilterPosition);

        final Spinner imageTypeFilterSpinner = (Spinner) view.findViewById(R.id.spinnerImageType);
        String[] imageTypes = ImageSearchConstants.imageTypeFilters.clone();
        imageTypes[0] = "any";
        ArrayAdapter imageTypeAdapter = new ArrayAdapter(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item, imageTypes);
        imageTypeFilterSpinner.setAdapter(imageTypeAdapter);
        imageTypeFilterSpinner.setSelection(searchParam.imageTypePosition);

        final TextView siteFilter = (TextView) view.findViewById(R.id.siteFilter);
        siteFilter.setText(searchParam.siteFilter);

        String title = getArguments().getString("title");
        getDialog().setTitle(title);
        getDialog().setCanceledOnTouchOutside(true);

        Button btCancel = (Button) view.findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btSave = (Button) view.findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchParam searchParam = new SearchParam();
                searchParam.imageTypePosition = imageTypeFilterSpinner.getSelectedItemPosition();
                searchParam.siteFilter = siteFilter.getText().toString();
                searchParam.colorFilterPosition = colorFilterSpinner.getSelectedItemPosition();
                searchParam.imageSizePosition = imageSizeSpinner.getSelectedItemPosition();
                searchParam.start = 0;
                searchParam.label = 0;
                Log.d("sending", searchParam.colorFilterPosition + " " + searchParam.imageSizePosition + " " + searchParam.imageTypePosition + " " + searchParam.siteFilter);

                Intent intent = new Intent("200");
                intent.putExtra("settings", searchParam);
                getActivity().sendBroadcast(intent) ;
                dismiss();
            }
        });


        return view;
    }


}
