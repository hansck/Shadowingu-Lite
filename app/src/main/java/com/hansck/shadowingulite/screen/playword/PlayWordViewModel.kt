package com.hansck.shadowingulite.screen.playword

import android.content.Context
import com.hansck.shadowingulite.model.Word
import com.hansck.shadowingulite.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class PlayWordViewModel(var context: Context?) {

	lateinit var word: Word

	fun setData(idWord: Int) {
		word = DataManager.instance.getWordById(idWord)
	}
}