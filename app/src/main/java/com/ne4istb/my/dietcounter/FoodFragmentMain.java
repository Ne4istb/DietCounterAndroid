package com.ne4istb.my.dietcounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static com.ne4istb.my.dietcounter.utils.UtilsHelper.println;

public class FoodFragmentMain extends Fragment {

    CaloriesRepository caloriesRepository;

    Button btnAddFood;
    TextView carbohydratesView;
    TextView proteinsView;
    TextView fatView;

    public static final int ADD_ITEM_CODE = 1;

    public static FoodFragmentMain newInstance() {
        return new FoodFragmentMain();
    }

    public FoodFragmentMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food_main, container, false);

        setHasOptionsMenu(true);
        getActivity().supportInvalidateOptionsMenu();

        caloriesRepository = new CaloriesRepository(getActivity());

        PercentView percentView = (PercentView) rootView.findViewById(R.id.percentview);

        percentView.setPercentage(40.0f);

        initViews(rootView);
        initListeners();

        updateData();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.food, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_reset) {
            caloriesRepository.resetCurrentState();
            updateData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_CODE && resultCode == getActivity().RESULT_OK)
            updateData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void updateData() {

        Float carbohydratesValue = 721.0f;
        Float proteinsValue = 245.0f;
        Float fatValue = 395.0f;

        List<Calories> caloriesList = caloriesRepository.getAllCalories();
        for(int i=0; i< caloriesList.size(); i++){
            carbohydratesValue -= caloriesList.get(i).getCarbohydrates();
            proteinsValue -= caloriesList.get(i).getProteins();
            fatValue -= caloriesList.get(i).getFat();
        }

        carbohydratesView.setText(carbohydratesValue.toString());
        proteinsView.setText(proteinsValue.toString());
        fatView.setText(fatValue.toString());
    }

    private void initViews(View rootView) {

        btnAddFood = (Button) rootView.findViewById(R.id.btnAddFood);

        carbohydratesView = (TextView) rootView.findViewById(R.id.txtCarbohydrates);
        proteinsView = (TextView) rootView.findViewById(R.id.txtProteins);
        fatView = (TextView) rootView.findViewById(R.id.txtFat);
    }

    private void initListeners() {
        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), AddFood.class), ADD_ITEM_CODE);
            }
        });
    }
}