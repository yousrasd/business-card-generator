package com.yousrasdn.businesscardgenerator.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yousrasdn.businesscardgenerator.data.local.dao.BusinessCardDao
import com.yousrasdn.businesscardgenerator.data.local.entity.BusinessCardEntity

@Database(
    entities = [BusinessCardEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun businessCardDao(): BusinessCardDao
}
