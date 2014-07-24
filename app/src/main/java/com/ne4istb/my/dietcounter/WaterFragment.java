package com.ne4istb.my.dietcounter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class WaterFragment extends Fragment {

    WaterRepository waterRepository;

    Button btnDrink;
    EditText editWaterVolumeView;
    TextView volumeView;

    public static WaterFragment newInstance() {
        return new WaterFragment();
    }

    public WaterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_water, container, false);

        setHasOptionsMenu(true);
        getActivity().supportInvalidateOptionsMenu();

        waterRepository = new WaterRepository(getActivity());

        initViews(rootView);
        initListeners();

        updateData();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.food, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_reset) {
            waterRepository.resetCurrentState();
            updateData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews(View rootView) {
        btnDrink = (Button) rootView.findViewById(R.id.btnDrink);
        editWaterVolumeView = (EditText) rootView.findViewById(R.id.editDrinkVolume);
        volumeView = (TextView) rootView.findViewById(R.id.txtVolume);
    }

    private void initListeners() {
        btnDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String volumeString = editWaterVolumeView.getText().toString();
                Float volume = volumeString.isEmpty() ? 0 : Float.valueOf(volumeString);

                waterRepository.add(volume * 0.001f);

                updateData();
            }
        });
    }

    private void updateData() {

        Float volume = 2.0f;

        List<Float> usedWaterVolumes = waterRepository.getUsedWater();
        for(int i=0; i< usedWaterVolumes.size(); i++){
            volume -= usedWaterVolumes.get(i);
        }

        volumeView.setText(volume.toString());
    }

}