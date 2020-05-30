package com.yoji.changecolorspinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner langSpinner;
    private Spinner themeSpinner;

    ArrayAdapter<CharSequence> langSpinnerAdapter;
    ArrayAdapter<CharSequence> themeSpinnerAdapter;
    private SharedPreferences langSharedPrefs;
    private final String LOCALE_KEY = "locale_key";
    private final String THEME_KEY = "theme_key";
    private Locale locale;
    private String chosenLocale;
    private int chosenThemeId;

    private AdapterView.OnItemSelectedListener themeSpinnerOnItemSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            chosenThemeId = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private AdapterView.OnItemSelectedListener langSpinnerOnItemSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case Language.RUSSIAN:
                    chosenLocale = "ru";
                    break;
                case Language.ENGLISH:
                    chosenLocale = "en";
                    break;
                case Language.SPANISH:
                    chosenLocale = "es";
                    break;
                case Language.GERMAN:
                    chosenLocale = "de";
                    break;
                case Language.FRENCH:
                    chosenLocale = "fr";
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    private View.OnClickListener okBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (chosenLocale != null) {
                //Create chosen locale
                locale = new Locale(chosenLocale);
                Configuration config = new Configuration();
                config.setLocale(locale);
                //Save chosen locale to SharedPrefs
                SharedPreferences.Editor editor = langSharedPrefs.edit();
                editor.putString(LOCALE_KEY, chosenLocale);
                editor.apply();
                //Set changes
                getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                recreate();
            }
            //Save chosen theme to SharedPrefs
            SharedPreferences.Editor editor = langSharedPrefs.edit();
            editor.putInt(THEME_KEY, chosenThemeId);
            editor.apply();
            Utils.changeToTheme(MainActivity.this, chosenThemeId);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        langSpinner = findViewById(R.id.langSpinnerId);
        themeSpinner = findViewById(R.id.colorSpinnerId);
        Button okButton = findViewById(R.id.okBtnId);

        langSharedPrefs = getSharedPreferences("Language", MODE_PRIVATE);

        langSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.language_array,
                android.R.layout.simple_spinner_dropdown_item);
        themeSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.color_array,
                android.R.layout.simple_spinner_dropdown_item);

        langSpinner.setAdapter(langSpinnerAdapter);
        langSpinner.setOnItemSelectedListener(langSpinnerOnItemSelectListener);
        themeSpinner.setAdapter(themeSpinnerAdapter);
        themeSpinner.setOnItemSelectedListener(themeSpinnerOnItemSelectListener);
        setLangSpinnerItem();
        setColorSpinnerItem();
        okButton.setOnClickListener(okBtnOnClickListener);
    }

    public void setLangSpinnerItem() {
        String currentLanguage;
        String[] languageArray = getResources().getStringArray(R.array.language_array);
        if (langSharedPrefs.getString(LOCALE_KEY, "").matches("")) {
            locale = Locale.getDefault();
        } else {
            locale = new Locale(langSharedPrefs.getString(LOCALE_KEY, ""));
        }
        switch (locale.getLanguage()) {
            case "ru":
                currentLanguage = languageArray[Language.RUSSIAN];
                break;
            case "en":
                currentLanguage = languageArray[Language.ENGLISH];
                break;
            case "es":
                currentLanguage = languageArray[Language.SPANISH];
                break;
            case "de":
                currentLanguage = languageArray[Language.GERMAN];
                break;
            case "fr":
                currentLanguage = languageArray[Language.FRENCH];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + locale.getLanguage());
        }
        langSpinner.setSelection(langSpinnerAdapter.getPosition(currentLanguage));
        String currentLocale = getResources().getConfiguration().locale.getLanguage();
        if (!currentLocale.equals(langSharedPrefs.getString(LOCALE_KEY, "")) && !langSharedPrefs.getString(LOCALE_KEY, "").matches("")) {
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            recreate();
        }
    }

    public void setColorSpinnerItem() {
        int sharedTheme = langSharedPrefs.getInt(THEME_KEY, 4);
        int currentTheme = Utils.getThemeResId();
        if (sharedTheme != 4) {
            themeSpinner.setSelection(sharedTheme);
            if (sharedTheme != currentTheme) {
                Utils.changeToTheme(MainActivity.this, sharedTheme);
            }
        }
    }
}