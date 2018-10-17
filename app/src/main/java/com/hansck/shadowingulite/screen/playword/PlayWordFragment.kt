package com.hansck.shadowingulite.screen.playword

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingulite.R
import com.hansck.shadowingulite.presentation.presenter.PlayPresenter
import com.hansck.shadowingulite.presentation.presenter.PlayWordPresenter
import com.hansck.shadowingulite.presentation.presenter.PlayWordPresenter.PlayWordView.ViewState.*
import com.hansck.shadowingulite.screen.base.BaseFragment
import com.hansck.shadowingulite.screen.play.PlayActivity
import com.hansck.shadowingulite.util.Common
import com.hansck.shadowingulite.util.PersistentManager
import kotlinx.android.synthetic.main.fragment_play_word.*
import smartdevelop.ir.eram.showcaseviewlib.GuideView


class PlayWordFragment : BaseFragment(), PlayWordPresenter.PlayWordView {

	private lateinit var model: PlayWordViewModel
	private lateinit var presenter: PlayWordPresenter
	private lateinit var bundle: Bundle
	private var guideIdx: Int = 0
	private lateinit var guides: Array<GuideView>

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_play_word, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		(activity as AppCompatActivity).supportActionBar!!.hide()
		init()
		presenter.presentState(SHOW_WORD)
	}

	private fun init() {
		this.model = PlayWordViewModel(activity)
		this.presenter = PlayWordPresenterImpl(this)
	}

	override fun showState(viewState: PlayWordPresenter.PlayWordView.ViewState) {
		when (viewState) {
			IDLE -> showProgress(false)
			LOADING -> showProgress(true)
			SHOW_WORD -> showWord()
			ERROR -> showError(null, getString(R.string.failed_request_general))
		}
	}

	override fun doRetrieveModel(): PlayWordViewModel = this.model

	private fun showWord() {
		bundle = this.arguments!!
		doRetrieveModel().setData(bundle.getInt("idWord"))

		val word = doRetrieveModel().word
		kanji.text = word.kanji
		furigana.text = word.furigana
		romaji.text = word.romaji
		meaning.text = word.meaning
		btnVoice.setOnClickListener {
			Common.instance.playAudio(activity!!, word.audio)
		}
		btnHint.setOnClickListener {
			presenter.presentState(WRONG_ANSWER)
			descriptionContainer.visibility = View.VISIBLE
			btnHint.visibility = View.GONE
		}
		btnNext.setOnClickListener {
			val act = (activity as PlayActivity)
			act.doRetrieveModel().nextWord()
			act.presenter.presentState(PlayPresenter.PlayView.ViewState.SHOW_WORD_SCREEN)
		}

		if (!PersistentManager.instance.isShowGuide()) {
			guides = arrayOf(buildGuide(kanji, "Word", resources.getString(R.string.guide_word)),
					buildGuide(btnVoice, "Voice", resources.getString(R.string.guide_voice)),
					buildGuide(btnHint, "Hint", resources.getString(R.string.guide_hint)),
					buildGuide(btnNext, "Next", resources.getString(R.string.guide_next)))
			showGuide()
		}
	}

	private fun showGuide() {
		if (guideIdx < guides.size) {
			guides[guideIdx].show()
		} else {
			PersistentManager.instance.setShowGuide()
		}
	}

	private fun buildGuide(view: View, title: String, content: String): GuideView {
		return GuideView.Builder(activity)
				.setTitle(title)
				.setContentText(content)
				.setTargetView(view)
				.setDismissType(GuideView.DismissType.anywhere)
				.setGuideListener {
					guideIdx++
					showGuide()
				}
				.build()
	}
}