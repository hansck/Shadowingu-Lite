package com.hansck.shadowingulite.presentation.presenter

import com.hansck.shadowingulite.presentation.base.BasePresenter
import com.hansck.shadowingulite.screen.play.PlayViewModel

/**
 * Created by Hans CK on 07-Jun-18.
 */
interface PlayPresenter : BasePresenter {

	interface PlayView {
		/**
		 * This enum is used for determine the current state of this screen
		 */
		enum class ViewState {
			IDLE, LOADING, SHOW_WORD_SCREEN, SHOW_FINISH_DIALOG, BACK_TO_HOME, RESET_PLAY,
			UPDATE_LESSON, UPDATE_LESSONS_PASSED, SHOW_SCREEN_STATE, ERROR
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
		fun doRetrieveModel(): PlayViewModel
	}

	/**
	 * This method is used for present the current state of this screen
	 *
	 * @param state
	 */
	fun presentState(state: PlayView.ViewState)
}