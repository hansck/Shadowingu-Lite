package com.hansck.shadowingulite.database

import android.annotation.SuppressLint
import android.util.Log
import com.hansck.shadowingulite.model.Lesson
import com.hansck.shadowingulite.presentation.App
import com.hansck.shadowingulite.presentation.customview.QueryListener
import com.hansck.shadowingulite.util.DataManager
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Hans CK on 13-Jun-18.
 */
class DBInteractor(var listener: QueryListener) {

	//region Lesson
	fun updateLessons(lesson: Lesson) {
		Completable.fromAction { App.database?.lessonDao()?.updateStage(lesson) }
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(object : CompletableObserver {
					override fun onSubscribe(d: Disposable) {

					}

					override fun onComplete() {
						listener.onQuerySucceed(QueryEnum.UPDATE_LESSONS)
					}

					override fun onError(e: Throwable) {
						listener.onQueryFailed(QueryEnum.UPDATE_LESSONS, e)
					}
				})
	}

	@SuppressLint("CheckResult")
	fun getLessons() {
		App.database?.lessonDao()?.getAll()
				?.subscribeOn(Schedulers.io())
				?.observeOn(AndroidSchedulers.mainThread())
				?.subscribe { lessons ->
					run {
						DataManager.instance.addLessons(lessons)
						listener.onQuerySucceed(QueryEnum.GET_LESSONS)
					}
				}
	}
	//endregion

	//region Word
	@SuppressLint("CheckResult")
	fun getWords() {
		App.database?.audioDao()?.getAll()
				?.subscribeOn(Schedulers.io())
				?.observeOn(AndroidSchedulers.mainThread())
				?.subscribe { audios ->
					run {
						DataManager.instance.addWords(audios)
						listener.onQuerySucceed(QueryEnum.GET_WORDS)
					}
				}
	}
	//endregion
}