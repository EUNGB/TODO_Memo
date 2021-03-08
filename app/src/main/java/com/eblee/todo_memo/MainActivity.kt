package com.eblee.todo_memo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eblee.todo_memo.entity.MemoEntity
import com.eblee.todo_memo.room.MemoDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), OnDeleteListener {

    lateinit var db: MemoDatabase
    var memoList = listOf<MemoEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = MemoDatabase.getInstance(this)!!
        getAllMemos()

        btn_add.setOnClickListener {
            val memo = MemoEntity(null, et_memo.text.toString())
            insertMemo(memo)
            et_memo.setText("")
            Utils.hideKeyboard(this)
        }

        rv_memo.layoutManager = LinearLayoutManager(this)
    }

    private fun insertMemo(memo: MemoEntity) {
        // 1. MainThread vs WorkerThread(Background Thread)
        // 모든 UI 관련된 일은 MainThread 에서 이루어져야한다.
        // 모든 데이터 관련 통신은 WorkerThread 에서 이루어져야한다.

        CoroutineScope(Dispatchers.IO).launch {
            db.memoDAO().insert(memo)
            withContext(Dispatchers.Main) {
                getAllMemos()
            }
        }
    }

    private fun getAllMemos() {
        CoroutineScope(Dispatchers.IO).launch {

            memoList = db.memoDAO().getAll()
            withContext(Dispatchers.Main) {
                setRecyclerView(memoList)
            }
        }
    }

    private fun deleteMemo(memo: MemoEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            db.memoDAO().delete(memo)
            withContext(Dispatchers.Main) {
                getAllMemos()
            }
        }
    }

    private fun setRecyclerView(memoList: List<MemoEntity>) {
        rv_memo.adapter = MemoAdapter(this, memoList, this)
    }

    override fun onDelete(memo: MemoEntity) {
        deleteMemo(memo)
    }

}