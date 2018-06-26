package jp.example.hoge.hinagata

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_local_data.*
import java.io.FileOutputStream
import java.io.IOException


// h. 内部ストレージ
// i. リスト表示 ListView
class activity_localFile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        // メニュー
        setSupportActionBar(temp_toolbar)
        temp_toolbar.setTitle(R.string.main_title)
        temp_toolbar.setTitleTextColor(Color.WHITE)
        temp_toolbar.setSubtitle(R.string.menu_msg2)
        temp_toolbar.setSubtitleTextColor(Color.WHITE)
    }

    // ADD
    fun onDataAdd(v: View) {
        val dat : String = LocalInputData.text.toString()
        LovalFileWrite(dat)
    }
    // READ
    fun onDataRead(v: View) {
        // リストを用意
        val listData : ArrayList<String> = ArrayList<String>()
        lovalFileRead(listData)

        // i. リスト表示
        //  ArrayListとListViewを対応付けるArrayAdapterを用意する
        val tmpAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData)
        // xmlのListViewで表示
        LocalList.setAdapter(tmpAdapter)
    }
    // DEL
    fun onDataDel(v: View) {
        try {
            // 内部ストレージ・ファイルの削除
            deleteFile(getString(R.string.localFileName))
        } catch ( e : IOException) {
            e.printStackTrace();
        }
    }
    // 内部ストレージのファイルにデータを追加
    private fun LovalFileWrite( saveData : String ) {
        try {
            // 追記書き込み
            val outputstream: FileOutputStream = openFileOutput(getString(R.string.localFileName), Context.MODE_APPEND)
            outputstream.write(saveData.toByteArray())
            outputstream.write("\n".toByteArray())
            outputstream.close()
        } catch (e: IOException) {
            e.printStackTrace();
        }
    }
    // 内部ストレージのファイルを読む
    private fun lovalFileRead( dataList : ArrayList<String> ) {
        try {
            val fis = openFileInput(getString(R.string.localFileName))
            val reader = fis.bufferedReader()
            for (lineBuffer in reader.readLines()) {
                dataList.add(lineBuffer)
            }
            reader.close()
            fis.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}