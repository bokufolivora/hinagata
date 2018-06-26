package jp.example.hoge.hinagata

import android.os.Bundle
import android.preference.*

// k. ユーザー設定用クラス
//  設定時の処理実態はここ
//  Preference は
//   PreferenceActivity ではなく  PreferenceFragment が推奨
//   PreferenceFragmentCompat は SupportLibrary v23から無くなった
class user_parameters  : PreferenceFragment() {
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 表示情報を追加
        addPreferencesFromResource(R.xml.parameters_seting)

        // 今のデータを呼び出して表示(サマリー)
        val switch = findPreference(getString(R.string.parameter_key_switch)) as SwitchPreference
        switch.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, value ->
            // 変更された場合は、表示を更新
            preference.summary = value.toString()

            // 値により、設定できる項目を変更
            val otherSettingsPreference = findPreference(getString(R.string.parameter_gr_setint2))
            otherSettingsPreference.isEnabled = value as Boolean

            true
        }
        switch.summary=switch.isChecked.toString()

        //
        val checkbox = findPreference(getString(R.string.parameter_key_checkbox)) as CheckBoxPreference
        checkbox.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, value ->
            preference.summary = value.toString()

            // 値により、設定できる項目を変更
            val otherSettingsPreference = findPreference(getString(R.string.parameter_gr_setint3))
            otherSettingsPreference.isEnabled = value as Boolean

            true
        }
        checkbox.summary=checkbox.isChecked.toString()

        // テキストが変更されたらサマリも更新する
        val text = findPreference(getString(R.string.parameter_key_text)) as EditTextPreference
        text.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, value ->
            preference.summary = value as CharSequence
            true
        }
        text.summary = text.text

        //
        val number = findPreference(getString(R.string.parameter_key_number)) as EditTextPreference
        number.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, value ->
            preference.summary = value as CharSequence
            true
        }
        number.summary = number.text

        val listSel = findPreference(getString(R.string.parameter_key_list)) as ListPreference
        listSel.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, value ->
            preference.summary = value as CharSequence
            true
        }
        listSel.summary = listSel.value


        findPreference(getString(R.string.parameter_gr_setint2)).isEnabled = switch.isChecked
        findPreference(getString(R.string.parameter_gr_setint3)).isEnabled = checkbox.isChecked
    }
}