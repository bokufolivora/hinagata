package jp.example.hoge.hinagata

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log

// android動作の整理用
// a. 丸いボタン		: FAB
// b. メッセージの表示	: Toast
//						: Snackbar
// c. 地域設定取得		: locale
// d. ネットワークの状態: ConnectivityManager
// e. メニュー			: Toolbar
// f. サイドメニュー	: NavigationView
// g. プリファレンス	: Preferences
// h. 内部ストレージ	:
// i. リスト表示		: ListView
// j. 外部ストレージ	:
// k. ユーザー設定画面	: PreferenceFragment
// l. データベース
// n. 外部ストレージ
// m. GPS
// o. グラフ表示		: MPAndroidChart
// p. DropBox			:
class MainActivity : AppCompatActivity() {
    // パーミッション
    private val REQUEST_PERMISSIONS_ID :Int  = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        checkPermission()
    }
    // パーミッションの確認
    fun checkPermission( ) {
        if (Build.VERSION.SDK_INT >= 23) {
            // Android 6.0 (APIレベル23)未満は、パーミッションはインストール時に確認

            // リクエストするパーミッションを保存するため
            val reqPermissions = ArrayList<String>()

            // m. ロケーション(GPS) パミッションの確認
            if (!when ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        PackageManager.PERMISSION_GRANTED -> {
                            false      // 許可済
                        }
                        PackageManager.PERMISSION_DENIED -> {
                            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                        else -> {
                            true
                        }
                    }) {
                // リクエスト に追加
                reqPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
// 上記コード　しつこく書くと
//          if (false == when ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//              PackageManager.PERMISSION_GRANTED -> {
//                false  // 許可済
//              }
//              PackageManager.PERMISSION_DENIED -> {
//                  if (false == ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                      // [今後表示しない]指定 -> (DENIED==checkSelfPermission) and ( false==shouldShowRequestPermissionRationale)
//                      false
//                  } else {
//                      true
//                  }
//              }
//              else -> {
//                  true
//              }
//          }) {
//              // リクエスト に追加
//              reqPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
//          }

            // n. 外部ストレージ パミッションの確認
            if (!when(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        PackageManager.PERMISSION_GRANTED -> {
                            // 許可済
                            false
                        }
                        PackageManager.PERMISSION_DENIED -> {
                            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        }
                        else -> {
                            true
                        }
                    }) {
                // リクエスト に追加
                reqPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            // リクエストの必要あり?
            if (!reqPermissions.isEmpty()) {
                // リクエスト
                ActivityCompat.requestPermissions(this, reqPermissions.toTypedArray(), REQUEST_PERMISSIONS_ID)
            } else {
                // 許可済
                appStartActivity()
            }

        } else {
            // なので、API23以下はインストールされ動いている場合は、許可済と判断
            Log.d("VERSION", "23以下")
            appStartActivity()
        }
    }

    // 結果 受け取り
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSIONS_ID) {
            if (grantResults.isNotEmpty()) {
                for (i in permissions.indices) {
                    if (permissions[i] == Manifest.permission.ACCESS_FINE_LOCATION) {
                        // m.ロケーションのパーミッション
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.d("LOCATION","true")
                        } else {
                            Log.d("LOCATION","false")
                        }
                    } else if (permissions[i] == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                        // n.外部ストレージのパーミッション
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.d("EXTERNAL","true")
                        } else {
                            Log.d("EXTERNAL","false")
                        }
                    }
                }
                appStartActivity()
            }
        }
    }

    private fun  appStartActivity() {
        // アプリの処理へ制御を渡す
        val intent = Intent(application, activity_hinagata::class.java)
        startActivity(intent)
        finish()
    }
}

