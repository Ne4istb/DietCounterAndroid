package com.ne4istb.my.dietcounter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ne4istb.my.dietcounter.R;

public class AddFood extends ActionBarActivity {

    CaloriesRepository caloriesRepository;

    Button okButtonView;
    Button cancelButtonView;

    EditText carbohydratesView;
    EditText proteinsView;
    EditText fatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        caloriesRepository = new CaloriesRepository(this);

        InitViews();
        InitListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void InitViews() {

        okButtonView = (Button) findViewById(R.id.btnEat);
        cancelButtonView = (Button) findViewById(R.id.btnCancelEat);

        carbohydratesView = (EditText) findViewById(R.id.editCarbohydrates);
        proteinsView = (EditText) findViewById(R.id.editProteins);
        fatView = (EditText) findViewById(R.id.editFat);
    }

    private void InitListeners() {

        okButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String carbohydratesString = carbohydratesView.getText().toString();
                Float carbohydrates = carbohydratesString.isEmpty() ? 0 : Float.valueOf(carbohydratesString);

                String proteinsString = proteinsView.getText().toString();
                Float proteins = proteinsString.isEmpty() ? 0 : Float.valueOf(proteinsString);

                String fatString = fatView.getText().toString();
                Float fat = fatString.isEmpty() ? 0 : Float.valueOf(fatString);

                caloriesRepository.add(carbohydrates, proteins, fat);

                closeActivity();
            }
        });

        cancelButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
    }

    private void closeActivity() {
        setResult(RESULT_OK);
        finish();
    }
}
