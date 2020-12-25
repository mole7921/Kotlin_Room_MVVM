package com.enzo.memolist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table")
    fun getTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}


/*
    Room不允許在mainThread進行存取操作,所以Dao定義的操作方法需suspend以讓Coroutine作動
    Room基本操作方法參考：https://ithelp.ithome.com.tw/articles/10223511
*/