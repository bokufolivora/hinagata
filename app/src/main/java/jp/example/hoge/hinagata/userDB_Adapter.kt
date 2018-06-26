package jp.example.hoge.hinagata

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.content.ContentValues
import android.database.Cursor
import java.text.SimpleDateFormat
import java.util.Date
import android.provider.SyncStateContract.Helpers.update


val DATABASE_NAME : String = "db_test1.db"
val DB_TABLE_NAME : String = "db_test1"
val DB_VERSION : Int = 1

// データベース用のクラス
class userDB_Helper (var mContext: Context?) : SQLiteOpenHelper(mContext, DATABASE_NAME, null, DB_VERSION) {

    //  SQLiteOpenHelper
    // 第１引数 :
    // 第２引数 : データベースの名称
    // 第３引数 : null
    // 第４引数 : データベースのバージョン

    override fun onCreate(db: SQLiteDatabase?) {
        // テーブルがなかったときに が呼ばれる
        // execSQL で　クエリSQL文を実行
        db?.execSQL(
                "CREATE TABLE " + DB_TABLE_NAME + " ( " +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "type integer not null, "+
                        "date text not null, " +
                        "memo text not null " +
                        ");")

//        Log.d("CREATE", DB_TABLE_NAME )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // バージョンが変わった時に実行される
        // 今回は,一度消して、作り直し　
        db?.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME + ";")
//        Log.d("DELECT", DB_TABLE_NAME )
        onCreate(db);
    }

}


// SQL文に対して、exeSQL()じゃなくメゾットを使ってみた
// 1. SELECT文                : query()
//                            : rawQuery()
//                            : queryWithFactory()
//                            : rawQueryWithFactory()
// 2. INSERT文                : insert()
//                            : insertOrThrow()
//                            : insertWithOnConflict()
// 3. UPDATE文                : update()
//                            : updateWithOnConflict()
// 4. DELETE文                : delete()
class userDB_Adapter(mContext: Context) {
    private val db: SQLiteDatabase
    private val uaerDB : userDB_Helper
    companion object {
//
    }
    init {
        uaerDB = userDB_Helper(mContext)
        db = uaerDB.getWritableDatabase()
    }

    fun isRecord( type:Int ,day:Int ) :Boolean {
        val selectQql : String = "SELECT count(*) as cnt FROM " + DB_TABLE_NAME + " where type = ? and date= ? "

        val cursor: Cursor = db.rawQuery(selectQql, arrayOf(type.toString(),day.toString()))
        cursor.moveToFirst()
        val count = cursor.getInt(cursor.getColumnIndex("cnt"))
        cursor.close()
        return if ( 0 < count ) { true } else { false }

    }


    // １レコード 追加
    fun addRecord(type:Int , day:Int ,memo:String) {
        //
        val values = ContentValues()
        values.put("memo", memo)
        values.put("date", day )
        values.put("type", type)

        // insertOrThrow()
        // 第1引数はテーブル名
        // 第2引数はデータを挿入する際にnull値が許可されていないカラムに代わりに利用される値を指定(?)
        // 第3引数は ContentValue(データ)
        db.insertOrThrow(DB_TABLE_NAME, null, values)
    }

    // Type,dateを指定してmemoを取得
    fun getMemo( type : Int, day : Int) : String {
        //
        val selectQql : String = "select * from " + DB_TABLE_NAME + " where type = ? and date= ? "
        // 第一引数にある?の部分に
        // 第二引数が入る（複数可能、先に指定した順)
        val cursor: Cursor = db.rawQuery(selectQql, arrayOf(type.toString(),day.toString()))
        var Result : String = ""
        try {
            if (cursor.moveToNext()) {
                Result = cursor.getString(cursor.getColumnIndex("memo"))
            }
        } finally {
            cursor.close()
        }
        return Result
    }

    // Type,dateを指定してmemoを修正
    fun updateMemo(type : Int, day : Int, memo : String ) {
        val values : ContentValues = ContentValues()
        values.put("memo",memo)

        // 第二引数がupdateする条件
        // 第三引数の? に第四引数が置き換わる
        db.update(DB_TABLE_NAME, values, "type=? AND date=? ", arrayOf(type.toString(),day.toString()))
    }

    //
    fun deleteRecord(type : Int, day : Int) {
        db.delete(DB_TABLE_NAME, "tepe=? AND date=? ", arrayOf(type.toString(),day.toString()))
    }


}
