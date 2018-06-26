package jp.example.hoge.hinagata

import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_local_data.*
import java.io.*

// j. 外部ストレージ
//    MainActivity.ktでパーミションの確認を行っている
//    xml/処理構成は内部ストレージのものと同じ
//    ファイルアクセスが多少変わる程度
class activity_extFile  : AppCompatActivity() {
    // アプリケーションフォルダー名
    private val mydirName : String = "smallSkill"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        setSupportActionBar(temp_toolbar)
        temp_toolbar.setTitle(R.string.main_title)
        temp_toolbar.setTitleTextColor(Color.WHITE)
        temp_toolbar.setSubtitle(R.string.menu_msg3)
        temp_toolbar.setSubtitleTextColor(Color.WHITE)
    }

    // ADD
    fun onDataAdd(v: View) {
        val dat : String = LocalInputData.text.toString()
        extFileWrite(dat)
    }
    // READ
    fun onDataRead(v: View) {
        // p. ListView の使い方
        // リストを用意
        val listData : ArrayList<String> = ArrayList<String>()
        extFileRead(listData)
        // リストとListViewを対応付けるArrayAdapterを用意する
        val tmpAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData)
        // xmlのListViewで表示
        LocalList.setAdapter(tmpAdapter)
    }
    // DEL
    fun onDataDel(v: View) {
        if ( false == isExternalStorageReadable )   return
        if ( false == isExternalStorageWritable )   return
        try {
            val fl = File(extFilePath)
            fl.delete()
//            deleteFile(extFilePath)
        } catch ( e : IOException) {
            e.printStackTrace();
        }
    }

    // 読み書き可能な外部ストレージがあるかどうかをチェック
    private val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }

    // 外部ストレージが読み取り可能かどうかをチェック
    private val isExternalStorageReadable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
        }

    // 現在の外部ストレージのログ・ファイル名(パス含め)
    private val extFilePath: String
        get() {
            val myDir = Environment.getExternalStorageDirectory().getPath() +"/"+mydirName
            return  myDir+"/"+ getString(R.string.ExtFileName)
        }

    // 外部ストレージのファイルにデータを追加
    private fun extFileWrite( saveData : String ) {
        if ( false == isExternalStorageReadable )   return
        if ( false == isExternalStorageWritable )   return

        // フォルダーを作る
        val myDir = File(Environment.getExternalStorageDirectory(), mydirName)
        if (!myDir.exists()) {
            myDir.mkdirs()
        }

        val file = File(extFilePath)
        try {
            FileOutputStream(file, true).use({ fileOutputStream ->
                OutputStreamWriter(fileOutputStream, "UTF-8").use({ outputStreamWriter ->
                    BufferedWriter(outputStreamWriter).use({ bw ->
                        bw.write(saveData+"\n")
                        bw.flush()
                    })
                })
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    // ストレージのファイルを読む
    private fun extFileRead( dataList : ArrayList<String> ) {
        if ( false == isExternalStorageReadable )   return

        try {
            FileInputStream(extFilePath).use { fileInputStream ->
                InputStreamReader(fileInputStream, "UTF8").use { inputStreamReader ->
                    BufferedReader(inputStreamReader).use { reader ->
                        for (lineBuffer in reader.readLines()) {
                            dataList.add(lineBuffer)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}