package com.hansck.shadowingulite.screen.play

import android.util.Log
import com.hansck.shadowingulite.database.DBInteractor
import com.hansck.shadowingulite.database.QueryEnum
import com.hansck.shadowingulite.presentation.customview.QueryListener
import com.hansck.shadowingulite.presentation.presenter.PlayPresenter
import com.hansck.shadowingulite.presentation.presenter.PlayPresenter.PlayView.ViewState.*
import com.hansck.shadowingulite.util.Constants
import com.hansck.shadowingulite.util.DataManager
import com.hansck.shadowingulite.util.FirebaseDB
import com.hansck.shadowingulite.util.PersistentManager
import java.util.*

/**
 * Created by Hans CK on 07-Jun-18.
 */
class PlayPresenterImpl(val view: PlayPresenter.PlayView) : PlayPresenter, QueryListener {

	private var interactor = DBInteractor(this)

	override fun presentState(state: PlayPresenter.PlayView.ViewState) {
		Log.i(PlayActivity::class.java.simpleName, state.name)
		when (state) {
			IDLE -> view.showState(IDLE)
			LOADING -> view.showState(LOADING)
			SHOW_WORD_SCREEN -> view.showState(SHOW_WORD_SCREEN)
			BACK_TO_HOME -> view.showState(BACK_TO_HOME)
			RESET_PLAY -> view.showState(RESET_PLAY)
			UPDATE_LESSON -> {
				view.showState(LOADING)
				interactor.updateLessons(view.doRetrieveModel().lesson)
			}
			UPDATE_LESSONS_PASSED -> updateLessonsPassed()
			SHOW_FINISH_DIALOG -> view.showState(SHOW_FINISH_DIALOG)
			SHOW_SCREEN_STATE -> view.showState(SHOW_SCREEN_STATE)
			ERROR -> view.showState(ERROR)
		}
	}

	override fun onAttach() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun onDetach() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun resume() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun pause() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun stop() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun destroy() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun onError(message: String) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun onQuerySucceed(route: QueryEnum) {
		when (route) {
			QueryEnum.UPDATE_LESSONS -> presentState(UPDATE_LESSONS_PASSED)
			else -> presentState(IDLE)
		}
	}

	override fun onQueryFailed(route: QueryEnum, throwable: Throwable) {
		presentState(ERROR)
	}

	private fun updateLessonsPassed() {
		val ref = FirebaseDB.instance.getDbReference(Constants.Database.USER)
		val taskMap = HashMap<String, Any>()
		taskMap["lessons_ungamified"] = DataManager.instance.getUnclearLevel()
		ref.child(PersistentManager.instance.getUserKey()).updateChildren(taskMap)
		presentState(SHOW_FINISH_DIALOG)
	}
}