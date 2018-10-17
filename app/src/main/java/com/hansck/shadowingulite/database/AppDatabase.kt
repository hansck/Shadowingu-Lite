package com.hansck.shadowingulite.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.hansck.shadowingulite.model.Lesson
import com.hansck.shadowingulite.model.Word
import java.util.concurrent.Executors


/**
 * Created by Hans CK on 11-Jun-18.
 */
@Database(entities = [(Word::class), (Lesson::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {

	abstract fun lessonDao(): LessonDao
	abstract fun audioDao(): WordDao

	companion object {
		private var INSTANCE: AppDatabase? = null

		fun getInstance(context: Context): AppDatabase? {
			if (INSTANCE == null) {
				synchronized(AppDatabase::class) {
					INSTANCE = Room.databaseBuilder(
							context.applicationContext,
							AppDatabase::class.java, "shadowingulite.db"
					)
							.addCallback(object : RoomDatabase.Callback() {
								override fun onCreate(db: SupportSQLiteDatabase) {
									// do something after database has been created
									Executors.newSingleThreadScheduledExecutor().execute {
										getInstance(context)?.lessonDao()?.insertAll(Lesson.populateData())
										getInstance(context)?.audioDao()?.insertAll(Word.populateData())
									}
								}

								override fun onOpen(db: SupportSQLiteDatabase) {
									// do something every time database is open
								}
							})
							.build()
				}
			}
			return INSTANCE
		}

		fun destroyInstance() {
			INSTANCE = null
		}
	}
}