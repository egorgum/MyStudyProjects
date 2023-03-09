package com.example.practice_18.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object: Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE words RENAME TO info")
        database.execSQL("ALTER TABLE info ADD COLUMN date TEXT ")
    }

}
