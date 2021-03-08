package com.eblee.todo_memo

import com.eblee.todo_memo.entity.MemoEntity

interface OnDeleteListener {
    fun onDelete(memo: MemoEntity)
}