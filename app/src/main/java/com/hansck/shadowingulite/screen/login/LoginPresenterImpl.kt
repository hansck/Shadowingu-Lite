package com.hansck.shadowingulite.screen.login

import android.os.Handler
import android.util.Log
import com.hansck.shadowingulite.database.DBInteractor
import com.hansck.shadowingulite.database.QueryEnum
import com.hansck.shadowingulite.presentation.customview.QueryListener
import com.hansck.shadowingulite.presentation.presenter.LoginPresenter
import com.hansck.shadowingulite.presentation.presenter.LoginPresenter.LoginView.ViewState.*
import com.hansck.shadowingulite.util.PersistentManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class LoginPresenterImpl(val view: LoginPresenter.LoginView) : LoginPresenter, QueryListener {

	private var interactor = DBInteractor(this)

	override fun presentState(state: LoginPresenter.LoginView.ViewState) {
		Log.i(LoginViewModel::class.java.simpleName, state.name)
		when (state) {
			IDLE -> view.showState(IDLE)
			LOADING -> view.showState(LOADING)
			ATTEMPT_LOGIN -> {
				presentState(LOADING)
				view.showState(ATTEMPT_LOGIN)
			}
			ENTER -> view.showState(ENTER)
			UPDATE_USER -> {
				PersistentManager.instance.setLogin()
				Handler().postDelayed({
					presentState(ENTER)
				}, 2000)
			}
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
	}

	override fun onQueryFailed(route: QueryEnum, throwable: Throwable) {
	}
}