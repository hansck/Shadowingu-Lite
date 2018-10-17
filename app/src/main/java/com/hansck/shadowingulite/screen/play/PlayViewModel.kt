package com.hansck.shadowingulite.screen.play

import android.content.Context
import com.hansck.shadowingulite.model.Lesson
import com.hansck.shadowingulite.model.Word
import com.hansck.shadowingulite.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class PlayViewModel(var context: Context?) {

    var words: List<Word> = ArrayList()
    lateinit var lesson: Lesson
    var currentWordId: Int = 0
    var count: Int = 10

    fun setData(idStage: Int) {
        lesson = DataManager.instance.lessons[idStage]
        words = DataManager.instance.getWordsByStage(idStage)
        currentWordId = words[0].idWord
    }

    fun calculatePlayResult() {
        lesson.cleared = true
        DataManager.instance.lessons[lesson.idLesson] = lesson
    }

    fun nextWord() {
        count--
        currentWordId++
    }

    fun resetPlay() {
        count = 10
        currentWordId = words[0].idWord
    }
}