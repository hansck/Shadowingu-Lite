package com.hansck.shadowingulite.presentation.presenter

import com.hansck.shadowingulite.presentation.base.BasePresenter
import com.hansck.shadowingulite.screen.home.HomeViewModel

/**
 * Created by Hans CK on 07-Jun-18.
 */
interface HomePresenter : BasePresenter {

	interface HomeView {
		/**
		 * This enum is used for determine the current state of this screen
		 */
		enum class ViewState {
			IDLE, LOADING, SHOW_ITEMS, SHOW_INTRO, SHOW_SCREEN_STATE, ERROR
		}

		enum class ScreenState {

		}

		/**
		 * This method is to show the current state of this screen
		 *
		 * @param viewState
		 */
		fun showState(viewState: ViewState)

		/**
		 * This function return the model that was belong to this screen
		 *
		 * @return
		 */
		fun doRetrieveModel(): HomeViewModel
	}

	/**
	 * This method is used for present the current state of this screen
	 *
	 * @param state
	 */
	fun presentState(state: HomeView.ViewState)
}