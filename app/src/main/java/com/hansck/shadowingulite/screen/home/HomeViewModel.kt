package com.hansck.shadowingulite.screen.home

import android.content.Context
import com.hansck.shadowingulite.model.*
import com.hansck.shadowingulite.presentation.adapter.SectionListAdapter
import com.hansck.shadowingulite.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class HomeViewModel(var context: Context?) {

	val categories = ArrayList<SectionListAdapter.Section>()
	var lessons: ArrayList<Lesson> = ArrayList()

	fun setStagesAndBadges() {
		lessons = DataManager.instance.lessons

		categories.clear()
		val titles: ArrayList<String> = ArrayList()
		for (i in lessons.indices) {
			if (lessons[i].category !in titles) {
				titles.add(lessons[i].category)
				categories.add(SectionListAdapter.Section(i, lessons[i].category))
			}
		}
	}
}