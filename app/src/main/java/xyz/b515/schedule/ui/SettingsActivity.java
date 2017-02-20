package xyz.b515.schedule.ui;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.b515.schedule.BuildConfig;
import xyz.b515.schedule.R;

public class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.settings_toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);
        getFragmentManager().beginTransaction().replace(R.id.content, new SettingsFragment()).commit();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public static class SettingsFragment extends PreferenceFragment {
        EditTextPreference userNamePreference;
        EditTextPreference psdNamePreference;
        Preference versionPreference;
        private static Preference.OnPreferenceChangeListener onPreferenceChangeListener = (preference, newValue) -> {
            String value = newValue.toString();
            preference.setSummary(value);
            if (preference.getKey().equals("password")) {
                preference.setSummary("********");
            }
            return true;
        };

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);

            userNamePreference = (EditTextPreference) findPreference("user");
            psdNamePreference = (EditTextPreference) findPreference("password");
            versionPreference = findPreference("version");

            versionPreference.setSummary(BuildConfig.VERSION_NAME);

            bindPreferenceSummaryToValue(userNamePreference);
            bindPreferenceSummaryToValue(psdNamePreference);
        }

        private static void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(onPreferenceChangeListener);
            onPreferenceChangeListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }
    }
}