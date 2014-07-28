package com.ne4istb.my.dietcounter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddFood extends ActionBarActivity {

    public static final int CARBOHYDRATES_COEF = 4;
    public static final int PROTEINS_COEF = 4;
    public static final int FAT_COEF = 9;
    CaloriesRepository caloriesRepository;

    Button okButtonView;
    Button cancelButtonView;

    EditText carbohydratesNorm;
    EditText proteinsNorm;
    EditText fatNorm;

    EditText foodAmount;

    TextView carbohydratesCal;
    TextView proteinsCal;
    TextView fatCal;

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

        carbohydratesNorm = (EditText) findViewById(R.id.editCarbohydratesNorm);
        proteinsNorm = (EditText) findViewById(R.id.editProteinsNorm);
        fatNorm = (EditText) findViewById(R.id.editFatNorm);

        foodAmount = (EditText) findViewById(R.id.editAmount);

        carbohydratesCal = (TextView) findViewById(R.id.txtCarbohydratesCalAmount);
        proteinsCal = (TextView) findViewById(R.id.txtProteinsCalAmount);
        fatCal = (TextView) findViewById(R.id.txtFatCalAmount);
    }

    private void InitListeners() {

        okButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float carbohydrates = calculateValue(carbohydratesNorm, CARBOHYDRATES_COEF);
                float proteins = calculateValue(proteinsNorm, PROTEINS_COEF);
                float fat = calculateValue(fatNorm, FAT_COEF);

                caloriesRepository.add(carbohydrates, proteins, fat);

                closeActivity();
            }
        });

        foodAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateCaloriesStatus(carbohydratesCal, carbohydratesNorm, CARBOHYDRATES_COEF);
                updateCaloriesStatus(proteinsCal, proteinsNorm, PROTEINS_COEF);
                updateCaloriesStatus(fatCal, fatNorm, FAT_COEF);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        carbohydratesNorm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateCaloriesStatus(carbohydratesCal, carbohydratesNorm, CARBOHYDRATES_COEF);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        proteinsNorm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateCaloriesStatus(proteinsCal, proteinsNorm, PROTEINS_COEF);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        fatNorm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateCaloriesStatus(fatCal, fatNorm, FAT_COEF);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        cancelButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
    }

    private void updateCaloriesStatus(TextView view, EditText editNorm, int coefficient) {
        float value = calculateValue(editNorm, coefficient);
        view.setText(Float.toString(value));
    }

    private float calculateValue(EditText editNorm, int coefficient) {

        String normString = editNorm.getText().toString();
        String grammsString = foodAmount.getText().toString();

        float normValue = normString.isEmpty() ? 0 : Float.valueOf(normString);
        float grammsValue = grammsString.isEmpty() ? 0 : Float.valueOf(grammsString);

        return grammsValue * (normValue / 100) * coefficient;
    }

    private void closeActivity() {
        setResult(RESULT_OK);
        finish();
    }
}
