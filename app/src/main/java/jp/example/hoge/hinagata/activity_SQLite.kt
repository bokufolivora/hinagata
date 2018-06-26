package jp.example.hoge.hinagata

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_user_db.*

// l.データベース
class activity_sqlite : AppCompatActivity() {

    //
    lateinit private var userDB : userDB_Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_db)

        // DBを呼び出している
        userDB = userDB_Adapter(this)
//
        // 今日の日付をデフォルトで表示
        val ff = SimpleDateFormat("YYYY/MM/dd")
        edit_date.setText( ff.format(Date()).toString() )
    }

    // DBへの書き込み
    fun onDataPut(v:View) {
        val day = stringDateToInt(edit_date.text.toString())
        if ( null != day ) {
            if ( false == userDB.isRecord((edit_type.text.toString()).toInt(),day )) {
                // 指定キーのデータなければ追加
                userDB.addRecord((edit_type.text.toString()).toInt(), day, edit_memo.text.toString())
            } else {
                // あれば修正
                userDB.updateMemo((edit_type.text.toString()).toInt(), day, edit_memo.text.toString())
            }
        }
    }
    // 読み出し
    fun onDataGet(v:View) {
        val day = stringDateToInt(edit_date.text.toString())
        if ( null != day ) {
            sql_memo_view.text = userDB.getMemo((edit_type.text.toString()).toInt(), day )
        }
    }

    // 日付からDBのキーを作成してる
    private val YBASE : Int = 2018
    // 日付をSQLでIntで使うため (なんとなく)
    private fun stringDateToInt( sDate : String ) : Int? {
        val f = SimpleDateFormat("yyyy/MM/dd")
        val fy = SimpleDateFormat("yyyy")
        val fd = SimpleDateFormat("D")
        try {
            val mDate = f.parse(sDate)
            val y : Int = fy.format(mDate).toInt()
            val ycnt : Int = if ( YBASE <= y ) { y - YBASE } else { 0 }
            val dcnt : Int = fd.format(mDate).toInt()
            return ( ycnt * 1000 ) + dcnt
        } catch (e: ParseException) {
            e.printStackTrace()
            return null
        }
    }

// 参考 :
//   http://android.techblog.jp/archives/8372325.html
//   https://qiita.com/Murofushi/items/bf32ae9d1d3d4abc98fe
//   http://m-shige1979.hatenablog.com/entry/2015/01/27/080000
}


