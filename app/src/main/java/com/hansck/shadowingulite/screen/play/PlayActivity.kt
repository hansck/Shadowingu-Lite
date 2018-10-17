package com.hansck.shadowingulite.screen.play

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.Window
import com.hansck.shadowingulite.R
import com.hansck.shadowingulite.presentation.presenter.PlayPresenter
import com.hansck.shadowingulite.presentation.presenter.PlayPresenter.PlayView.ViewState.*
import com.hansck.shadowingulite.screen.base.BaseActivity
import com.hansck.shadowingulite.screen.base.BaseFragment
import com.hansck.shadowingulite.screen.dialog.FinishDialog
import com.hansck.shadowingulite.screen.playword.PlayWordFragment


class PlayActivity : BaseActivity(), PlayPresenter.PlayView {

    private lateinit var model: PlayViewModel
    lateinit var presenter: PlayPresenter
    lateinit var fm: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_play)
        init()
    }

    private fun init() {
        this.model = PlayViewModel(this)
        this.presenter = PlayPresenterImpl(this)
        fm = supportFragmentManager

        doRetrieveModel().setData(intent.extras.getInt("idLesson"))
        presenter.presentState(SHOW_WORD_SCREEN)
    }

    override fun showState(viewState: PlayPresenter.PlayView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            SHOW_WORD_SCREEN -> showFragment()
            SHOW_FINISH_DIALOG -> showFinishDialog()
            BACK_TO_HOME -> backToHome()
            RESET_PLAY -> {
                doRetrieveModel().resetPlay()
                presenter.presentState(SHOW_WORD_SCREEN)
            }
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): PlayViewModel = this.model

    private fun showFragment() {
        Log.e("COUNT", doRetrieveModel().count.toString())
        if (doRetrieveModel().count > 0) {
            val fragment: BaseFragment = PlayWordFragment()
            val bundle = Bundle()
            bundle.putInt("idWord", doRetrieveModel().currentWordId)
            fragment.arguments = bundle
            navigateTo(fm, fragment)
        } else {
            doRetrieveModel().calculatePlayResult()
            presenter.presentState(UPDATE_LESSON)
        }
        presenter.presentState(IDLE)
    }

    private fun showFinishDialog() {
        val playResultDialog = FinishDialog()
        playResultDialog.show(fm, "playResult")
    }

    private fun backToHome() {
        onBackPressed()
    }
}
