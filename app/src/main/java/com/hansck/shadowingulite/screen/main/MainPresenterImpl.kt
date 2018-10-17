package com.hansck.shadowingulite.screen.main

import android.util.Log
import com.hansck.shadowingulite.database.DBInteractor
import com.hansck.shadowingulite.database.QueryEnum
import com.hansck.shadowingulite.presentation.customview.QueryListener
import com.hansck.shadowingulite.presentation.presenter.MainPresenter
import com.hansck.shadowingulite.presentation.presenter.MainPresenter.MainView.ViewState.*

/**
 * Created by Hans CK on 07-Jun-18.
 */
class MainPresenterImpl(val view: MainPresenter.MainView) : MainPresenter, QueryListener {

	private var interactor = DBInteractor(this)

	override fun presentState(state: MainPresenter.MainView.ViewState) {
		Log.i(MainViewModel::class.java.simpleName, state.name)
		when (state) {
			IDLE -> view.showState(IDLE)
			LOADING -> view.showState(LOADING)
			LOAD_STAGES -> interactor.getLessons()
			LOAD_AUDIOS -> interactor.getWords()
			LOAD_HOME -> view.showState(LOAD_HOME)
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
			QueryEnum.GET_LESSONS -> presentState(LOAD_AUDIOS)
			QueryEnum.GET_WORDS -> presentState(LOAD_HOME)
			else -> {
				presentState(LOAD_HOME)
			}
		}
	}

	override fun onQueryFailed(route: QueryEnum, throwable: Throwable) {
		presentState(ERROR)
	}
}