package jp.example.hoge.hinagata

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_preferences_data.*

// g. Preferences プリファレンス（＝アプリローカルなデータ)
class activity_preferences : AppCompatActivity() {
    // アクセス識別用のキーワード
    private val PREFERENCES_DATA1_DEF : String ="Message"
    private val PREFERENCES_DATA2_DEF : Int = 0
    private val PREFERENCES_DATA3_DEF : Boolean = false

    // プリファレンス準備
    lateinit var prefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences_data)

        // プリファレンスを呼びだしておく
        prefs =getSharedPreferences(getString(R.string.preferences_key_data), AppCompatActivity.MODE_PRIVATE)


        // メニュー
        setSupportActionBar(temp_toolbar)
        temp_toolbar.setTitle(R.string.main_title)
        temp_toolbar.setTitleTextColor(Color.WHITE)
        temp_toolbar.setSubtitle(R.string.menu_msg1)
        temp_toolbar.setSubtitleTextColor(Color.WHITE)
    }
    // 1. 項目 MAS
    fun onMasSave(v: View) {
        val msgData : String = msgInput.text.toString()
        val e : SharedPreferences.Editor = prefs.edit()
        // 保存
        e.putString(getString(R.string.preferences_key_data1) , msgData)
        e.apply()
    }
    fun onMsgRead(v: View) {
        // 読み出し
        msgViewR.text = prefs.getString(getString(R.string.preferences_key_data1), PREFERENCES_DATA1_DEF)
    }

    // 2.項目 COUNT
    fun onCountSave(v: View) {
        val countData : Int = countInput.text.toString().toInt()
        val e : SharedPreferences.Editor = prefs.edit()
        // 保存
        e.putInt(getString(R.string.preferences_key_data2), countData)
        e.apply()
    }
    fun onCountRead(v: View) {
        // 読み出し
        countViewR.text = prefs.getInt(getString(R.string.preferences_key_data2), PREFERENCES_DATA2_DEF).toString()
    }

    // 3.項目 Switch
    fun onSwitchSave(v: View) {
        val switchData : Boolean = switch1.isChecked
        val e : SharedPreferences.Editor = prefs.edit()
        // 保存
        e.putBoolean(getString(R.string.preferences_key_data3), switchData)
        e.apply()
    }
    fun onSwitchRead(v: View) {
        // 読みだだし
        switchViewR.text = prefs.getBoolean(getString(R.string.preferences_key_data3), PREFERENCES_DATA3_DEF).toString()
    }
}