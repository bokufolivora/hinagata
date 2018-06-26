package jp.example.hoge.hinagata

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_hinagata.*
import java.util.*

// a. FAB       丸いボタン
// b. Toast     メッセージの表示
//    Snackbar  画面下部への表示
// c. locale    地域設定取得
// d. ConnectivityManager   ネットワークの状態
// e. Toolbar  ツールバー
//    
// f. NavigationView サイドメニュー
class activity_hinagata : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hinagata)


        // e. メニューを Toolbar で
        // styles.xml の DarkActionBar を NoActionBar　変更してタイトルバーを消す
        // xml に android.support.v7.widget.Toolbar
        // ツールバーをアクションバーとしてセット
        setSupportActionBar(main_toolbar)
        main_toolbar.setTitle(R.string.main_title)
        main_toolbar.setTitleTextColor(Color.WHITE)
        main_toolbar.setSubtitle(R.string.sub_title)
        main_toolbar.setSubtitleTextColor(Color.WHITE)
        // xml.Toolbar に app:navigationIcon=""　設定するとアイコンでるがバランス難しい

        // f. サイドバー表示
        // トグル(sw) : アクションバー(サイドメニュー)の表示切替に使用
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                main_drawerlayout,
                main_toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            override fun onDrawerClosed(mview: View) {
                super.onDrawerClosed(mview)
            }

            override fun onDrawerOpened(mview: View) {
                super.onDrawerOpened(mview)
            }
        }
        // サイドメニュー項目を動的に変更する場合
        val tmpm: Menu = side_navigation.getMenu()
        tmpm.findItem(R.id.navigation_item_0).setTitle(getString(R.string.sied_menu_item0))
        tmpm.findItem(R.id.sub_item1).setVisible(false)
        //
        drawerToggle.isDrawerIndicatorEnabled = true
        main_drawerlayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        // サイドメニュー選択された時の処理
        //  onOptionsItemSelectedでも処理できるが、メニューと分けるためここに記載してみました
        side_navigation.setNavigationItemSelectedListener {
            // サンプル見ると onNavigationItemSelected() だはなく無形関数で定義してる
            when (it.itemId) {
                R.id.navigation_item_0 -> {
                    val intent = Intent(this, activity_gps::class.java)
                    startActivity(intent)
                }
                R.id.navigation_item_1 -> {
                    val intent = Intent(this, activity_graph::class.java)
                    startActivity(intent)
                }
                R.id.navigation_item_2 -> {
                    tmpLowDisp("item-2")
                }
                R.id.sub_item0 -> {
                    val tmpm0: Menu = side_navigation.getMenu()
                    tmpm0.findItem(R.id.sub_item0).setVisible(false)
                    tmpm0.findItem(R.id.sub_item1).setVisible(true)
                    tmpLowDisp("Sub item-0")
                }
                R.id.sub_item1 -> {
                    val tmpm1: Menu = side_navigation.getMenu()
                    tmpm1.findItem(R.id.sub_item0).setVisible(true)
                    tmpm1.findItem(R.id.sub_item1).setVisible(false)
                    tmpLowDisp("Sub item-1")
                }
                else -> {
                    tmpLowDisp(it.itemId.toString())
                }
            }
            // ナビゲーションを閉じる
            main_drawerlayout.closeDrawer(GravityCompat.START)
            // setNavigationItemSelectedListener は Boolean型
            true
        }


        // a.FAB
        // 1. build.gradle に　implementation 'com.android.support:design:'　
        // 2. xml に　android.support.design.widget.FloatingActionButton
        // 3. setOnClickListener で処理を記載
        fAB.setOnClickListener { view ->
            tmpLowDisp("FABが押されました")
        }

        // 地域設定の表示
        locale_view.text = getLocale()

    }

    // e. メニューの表示
    //   res/menuフォルダーにメニューxml作成
    override fun onCreateOptionsMenu(mmenu: Menu): Boolean {
        // メニューの表示
        getMenuInflater().inflate(R.menu.main_manu, mmenu)
        return true
    }
    // ここでメニュー項目を調整できる
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_settings0).setVisible(false)
        menu.findItem(R.id.action_settings4).setTitle(R.string.menu_msg4)
        menu.findItem(R.id.action_add).setVisible(false)
        return true
    }
    // メニュー選択された時の処理
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings1 -> {
                val intent = Intent(this, activity_preferences::class.java)
                startActivity(intent)
                true
            }
            R.id.action_settings2 -> {
                val intent = Intent(this, activity_localFile::class.java)
                startActivity(intent)
                true
            }
            R.id.action_settings3 -> {
                val intent = Intent(this, activity_extFile::class.java)
                startActivity(intent)
                true
            }
            R.id.action_settings4 -> {
                val intent = Intent(this, activity_sqlite::class.java)
                startActivity(intent)
                true
            }
            R.id.action_settings5 -> {
                val intent = Intent(this, actitvity_userSeting::class.java)
                startActivity(intent)
                true
            }
            R.id.action_search -> {
                tmpLowDisp(item.title.toString())
                true
            }
            R.id.action_add -> {
                tmpLowDisp(item.title.toString())
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    // d. ネットワーク状態の確認
    //   動作サンプル用のボタン,
    fun onCmdWireless(v: View) {
        wirelessView.text = if (false == isOnline(this)) {
            "未接続"
        } else if (true == isWifiConnected(this)) {
            "Wifi"
        } else {
            "mobile"
        }
    }
    //
    private fun isOnline(context: Context): Boolean {
        // 接続しているか？
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
    //
    private fun isWifiConnected(context: Context): Boolean {
        // Wifiか？
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return (networkInfo != null && networkInfo.isConnected
                && networkInfo.type == ConnectivityManager.TYPE_WIFI)
    }

    // c. ロケール（端末の地域設定)の取得
    //    values-ja/strings.xml に日本語用意しておけば、R.string.の参照先 自動で変わる
    private fun getLocale(): String {
        // ｃ. ロケールの取得
        val locale = Locale.getDefault()
        val language = locale.getLanguage()
        val country = locale.getCountry()
        Log.d("County", country)
        return if (language == "ja") {
            "日本"
        } else if (language == "en") {
            "英語"
        } else {
            "他"
        }
    }


    // b. Toast 下部へのメッセージ一時表示　
    private fun Context.tmpLowDisp(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
    // 動作サンプル用ボタン　メッセージ表示１
    fun onCmdSnackbar1(v: View) {
        // b. Snackbar 下部へのメッセージ表示(シンプルは表示)
        Snackbar.make(v, "Snackbarの表示", Snackbar.LENGTH_LONG).show()
    }
    // 動作サンプル用ボタン　メッセージ表示２
    fun onCmdSnackbar2(v: View) {
        // b. Snackbar 下部へのメッセージ表示　（表示内容を調整して)
        val snackbar = Snackbar.make(v, "画面下部へ", Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(Color.RED)
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.setAction("更新") {
            // 更新の処理
            tmpLowDisp("更新")
        }
        snackbar.show()
    }

}
