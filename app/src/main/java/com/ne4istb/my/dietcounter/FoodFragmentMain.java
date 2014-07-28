package com.ne4istb.my.dietcounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FoodFragmentMain extends Fragment {

    public static final float CARBOHYDRATES_NORM = 721.0f;
    public static final float PROTEINS_NORM = 245.0f;
    public static final float FAT_NORM = 395.0f;

    CaloriesRepository caloriesRepository;

    Button btnAddFood;

    TextView carbohydratesEaten;
    TextView carbohydratesLeft;
    TextView proteinsEaten;
    TextView proteinsLeft;
    TextView fatEaten;
    TextView fatLeft;

    CirclePiesView piesView;

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

        float carbohydratesEatenValue = 0.0f;
        float proteinsEatenValue = 0.0f;
        float fatEatenValue = 0.0f;

        List<Calories> caloriesList = caloriesRepository.getAllCalories();
        for(int i=0; i< caloriesList.size(); i++){
            carbohydratesEatenValue += caloriesList.get(i).getCarbohydrates();
            proteinsEatenValue += caloriesList.get(i).getProteins();
            fatEatenValue += caloriesList.get(i).getFat();
        }

        carbohydratesEaten.setText(Float.toString(carbohydratesEatenValue));
        proteinsEaten.setText(Float.toString(proteinsEatenValue));
        fatEaten.setText(Float.toString(fatEatenValue));

        carbohydratesLeft.setText(Float.toString(CARBOHYDRATES_NORM - carbohydratesEatenValue));
        proteinsLeft.setText(Float.toString(PROTEINS_NORM - proteinsEatenValue));
        fatLeft.setText(Float.toString(FAT_NORM - fatEatenValue));


        updateNormPies(carbohydratesEatenValue, proteinsEatenValue, fatEatenValue);
    }

    private void updateNormPies(Float carbohydratesEatenValue, Float proteinsEatenValue, Float fatEatenValue) {

        List<Pair<Float, Integer>> pies = new ArrayList<Pair<Float, Integer>>();

        float circleValue = CARBOHYDRATES_NORM + PROTEINS_NORM + FAT_NORM;

        pies.add(new Pair<Float, Integer>((CARBOHYDRATES_NORM - carbohydratesEatenValue)/circleValue,
                getActivity().getResources().getColor(R.color.carbohydrates_left)));
        pies.add(new Pair<Float, Integer>(carbohydratesEatenValue/circleValue,
                getActivity().getResources().getColor(R.color.carbohydrates_used)));

        pies.add(new Pair<Float, Integer>((PROTEINS_NORM - proteinsEatenValue)/circleValue,
                getActivity().getResources().getColor(R.color.proteins_left)));
        pies.add(new Pair<Float, Integer>(proteinsEatenValue/circleValue,
                getActivity().getResources().getColor(R.color.proteins_used)));

        pies.add(new Pair<Float, Integer>((FAT_NORM - fatEatenValue)/circleValue,
                getActivity().getResources().getColor(R.color.fat_left)));
        pies.add(new Pair<Float, Integer>(fatEatenValue/circleValue,
                getActivity().getResources().getColor(R.color.fat_used)));

        piesView.setPies(pies);
    }

    private void initViews(View rootView) {

        btnAddFood = (Button) rootView.findViewById(R.id.btnAddFood);

        carbohydratesEaten = (TextView) rootView.findViewById(R.id.txtCarbohydratesEaten);
        carbohydratesLeft= (TextView) rootView.findViewById(R.id.txtCarbohydratesLeft);

        proteinsEaten = (TextView) rootView.findViewById(R.id.txtProteinsEaten);
        proteinsLeft = (TextView) rootView.findViewById(R.id.txtProteinsLeft);

        fatEaten = (TextView) rootView.findViewById(R.id.txtFatEaten);
        fatLeft = (TextView) rootView.findViewById(R.id.txtFatLeft);

        piesView = (CirclePiesView) rootView.findViewById(R.id.piesView);
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