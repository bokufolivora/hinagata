package jp.example.hoge.hinagata

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_gps.*

// m. GPS
class activity_gps  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)

        setSupportActionBar(temp_toolbar)
        temp_toolbar.setTitle(R.string.main_title)
        temp_toolbar.setTitleTextColor(Color.WHITE)
        temp_toolbar.setSubtitle(R.string.sied_menu_item0)
        temp_toolbar.setSubtitleTextColor(Color.WHITE)

        // 位置情報使用時に準備
         if ((PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION  )) and
             (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)))
         {

            // 位置情報を管理している LocationManager のインスタンスを生成
            var locationManager: LocationManager? = getSystemService(LOCATION_SERVICE) as LocationManager
            var locationProvider : String = ""

            if (null !== locationManager) {
                // GPSが利用可能になっているかどうかをチェック
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    locationProvider = LocationManager.GPS_PROVIDER
                } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    locationProvider = LocationManager.NETWORK_PROVIDER
                } else {
                    // いずれも利用可能でない場合は、GPSを設定する画面に
                    val settingsIntent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(settingsIntent)
                    return
                }

                // 変化検出時によだれる
                locationManager.requestLocationUpdates(
                        locationProvider,
                        500, // 通知のための最小時間間隔（ミリ秒）
                        1.0F, // 通知のための最小距離間隔（メートル）
                        object : LocationListener {
                            override fun onLocationChanged(location: Location) {
                                val lat = location.latitude
                                val lng = location.longitude
                                LatitudeView.text = R.id.LongtudeView.toString() +" : " + lat.toString()
                                LongtudeView.text = R.id.LongtudeView.toString() +" : " + lng.toString()
                                Log.d("GPS", lat.toString() + "-" + lng.toString())
                            }
                            override fun onProviderDisabled(provider: String) {}
                            override fun onProviderEnabled(provider: String) {}
                            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                        }
                )

            }
        }
    }
}