package com.hansck.shadowingulite.screen.home


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingulite.R
import com.hansck.shadowingulite.presentation.adapter.LessonsAdapter
import com.hansck.shadowingulite.presentation.adapter.SectionListAdapter
import com.hansck.shadowingulite.presentation.customview.OnLessonSelected
import com.hansck.shadowingulite.presentation.presenter.HomePresenter
import com.hansck.shadowingulite.presentation.presenter.HomePresenter.HomeView.ViewState.*
import com.hansck.shadowingulite.screen.base.BaseFragment
import com.hansck.shadowingulite.screen.dialog.IntroductionDialog
import com.hansck.shadowingulite.screen.play.PlayActivity
import com.hansck.shadowingulite.util.PersistentManager
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : BaseFragment(), HomePresenter.HomeView, OnLessonSelected {

	private lateinit var model: HomeViewModel
	private lateinit var presenter: HomePresenter
	private var adapter: LessonsAdapter? = null
	lateinit var fm: FragmentManager

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_home, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		init()
		if (!PersistentManager.instance.isFirstIntro()) {
			presenter.presentState(SHOW_INTRO)
		} else {
			presenter.presentState(SHOW_ITEMS)
		}
	}

	override fun onResume() {
		super.onResume()
		adapter?.notifyDataSetChanged()
	}

	private fun init() {
		this.model = HomeViewModel(activity)
		this.presenter = HomePresenterImpl(this)
		fm = activity!!.supportFragmentManager
	}

	override fun showState(viewState: HomePresenter.HomeView.ViewState) {
		when (viewState) {
			IDLE -> showProgress(false)
			LOADING -> showProgress(true)
			SHOW_ITEMS -> showItems()
			SHOW_INTRO -> showIntroDialog()
			ERROR -> showError(null, getString(R.string.failed_request_general))
		}
	}

	override fun doRetrieveModel(): HomeViewModel = this.model

	override fun onStageSelected(lesson: com.hansck.shadowingulite.model.Lesson) {
		val intent = Intent(activity, PlayActivity::class.java)
		intent.putExtra("idLesson", lesson.idLesson)
		startActivity(intent)
	}

	private fun showItems() {
		// Set Lesson List
		doRetrieveModel().setStagesAndBadges()
		stageList.setHasFixedSize(true)
		stageList.layoutManager = LinearLayoutManager(context)
		adapter = LessonsAdapter(doRetrieveModel().lessons, false, this)

		// show the data
		val dummy = arrayOfNulls<SectionListAdapter.Section>(doRetrieveModel().categories.size)
		val mSectionedAdapter = SectionListAdapter(activity, R.layout.item_section, R.id.section_text, stageList, adapter)
		mSectionedAdapter.setSections(doRetrieveModel().categories.toArray(dummy))
		stageList.adapter = adapter
		stageList.adapter = mSectionedAdapter
	}

	private fun showIntroDialog() {
		PersistentManager.instance.setFirstIntro()
		val introDialog = IntroductionDialog()
		introDialog.show(fm, "intro")
		presenter.presentState(SHOW_ITEMS)
	}
}
