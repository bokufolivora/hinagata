package jp.example.hoge.hinagata

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_user_seting.*


// k. ユーザー設定画面 PreferenceFragment
class actitvity_userSeting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_seting)

        // メニュー
        set_toolbar.setTitle(R.string.main_title)
        set_toolbar.setTitleTextColor(Color.WHITE)
        set_toolbar.setSubtitle(R.string.menu_msg5)
        set_toolbar.setSubtitleTextColor(Color.WHITE)

        // ユーザー設定を呼び出し
        val tsp = user_parameters()
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, tsp )
                .commit()
    }

}