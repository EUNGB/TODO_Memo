package com.eblee.todo_memo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eblee.todo_memo.entity.MemoEntity
import com.eblee.todo_memo.room.MemoDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    lateinit var db: MemoDatabase
    var memoList = listOf<MemoEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = MemoDatabase.getInstance(this)!!

        btn_add.setOnClickListener {
            val memo = MemoEntity(null, et_memo.text.toString())
            insertMemo(memo)
        }
    }

    private fun insertMemo(memo: MemoEntity) {
        // 1. MainThread vs WorkerThread(Background Thread)
        // 모든 UI 관련된 일은 MainThread 에서 이루어져야한다.
        // 모든 데이터 관련 통신은 WorkerThread 에서 이루어져야한다.

        runBlocking {
            db.memoDAO().insert(memo)
        }
        getAllMemos()
    }

    private fun getAllMemos() {
        runBlocking {
            memoList = db.memoDAO().getAll()
        }
        setRecyclerView(memoList)
    }

    fun deleteMemo() {

    }

    private fun setRecyclerView(memoList: List<MemoEntity>) {

    }

}