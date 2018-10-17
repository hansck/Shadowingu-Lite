package com.hansck.shadowingulite.util

import com.hansck.shadowingulite.model.Lesson
import com.hansck.shadowingulite.model.Word

/**
 * Created by Hans CK on 13-Jun-18.
 */
class DataManager {

	companion object {
		val instance = DataManager()
	}

	var lessons: ArrayList<Lesson> = ArrayList()
	var words: ArrayList<Word> = ArrayList()

	fun addLessons(list: List<Lesson>) {
		lessons.clear()
		lessons.addAll(list)
		lessons[0].cleared = true
		lessons[1].cleared = true
//		lessons[2].cleared = true
//		lessons[3].cleared = true
//		lessons[4].cleared = true
//		lessons[5].cleared = true
//		lessons[6].cleared = true
	}

	fun addWords(list: List<Word>) {
		words.clear()
		words.addAll(list)
	}

	fun getWordById(idWord: Int): Word {
		return words.first { it.idWord == idWord }
	}

	fun getWordsByStage(idStage: Int): List<Word> {
		return words.filter { it.stage == idStage }
	}

	fun getUnclearLevel(): Int {
		return lessons.filter { it.cleared }.size
	}
}