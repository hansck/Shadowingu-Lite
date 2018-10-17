package com.hansck.shadowingulite.util

import android.content.SharedPreferences

/**
 * Created by Hans CK on 26-Jun-18.
 */
class PersistentManager {

	companion object {
		val instance = PersistentManager()
	}

	lateinit var keyStore: SharedPreferences

	fun clearKeyStore() {
		keyStore.edit().clear().apply()
	}

	fun isLogin(): Boolean {
		return keyStore.getBoolean(Constants.Preferences.IS_LOGIN, false)
	}

	fun setLogin() {
		val editor = keyStore.edit()
		editor.putBoolean(Constants.Preferences.IS_LOGIN, true)
		editor.apply()
	}

	fun setUserKey(id: String) {
		val editor = keyStore.edit()
		editor.putString(Constants.Database.KEY, id)
		editor.apply()
	}

	fun getUserKey(): String {
		return keyStore.getString(Constants.Database.KEY, "")
	}

	fun setActiveAvatar(idAvatar: Int) {
		val editor = keyStore.edit()
		editor.putInt(Constants.Preferences.ACTIVE_AVATAR, idAvatar)
		editor.apply()
	}

	fun getActiveAvatar(): Int {
		return keyStore.getInt(Constants.Preferences.ACTIVE_AVATAR, 0)
	}

	fun setPerfectPlay() {
		val editor = keyStore.edit()
		editor.putBoolean(Constants.Preferences.PERFECT_PLAY, true)
		editor.apply()
	}

	fun isPerfectPlay(): Boolean {
		return keyStore.getBoolean(Constants.Preferences.PERFECT_PLAY, false)
	}

	fun setFirstBuy() {
		val editor = keyStore.edit()
		editor.putBoolean(Constants.Preferences.FIRST_BUY, true)
		editor.apply()
	}

	fun isFirstBuy(): Boolean {
		return keyStore.getBoolean(Constants.Preferences.FIRST_BUY, false)
	}

	fun setFirstGameOver() {
		val editor = keyStore.edit()
		editor.putBoolean(Constants.Preferences.FIRST_GAME_OVER, true)
		editor.apply()
	}

	fun isFirstGameOver(): Boolean {
		return keyStore.getBoolean(Constants.Preferences.FIRST_GAME_OVER, false)
	}

	fun setMaxLevel() {
		val editor = keyStore.edit()
		editor.putBoolean(Constants.Preferences.MAX_LEVEL, true)
		editor.apply()
	}

	fun isMaxLevel(): Boolean {
		return keyStore.getBoolean(Constants.Preferences.MAX_LEVEL, false)
	}

	fun setAllStagesCleared() {
		val editor = keyStore.edit()
		editor.putBoolean(Constants.Preferences.ALL_LESSONS_CLEAR, true)
		editor.apply()
	}

	fun isAllStagesCleared(): Boolean {
		return keyStore.getBoolean(Constants.Preferences.ALL_LESSONS_CLEAR, false)
	}

	fun setShowGuide() {
		val editor = keyStore.edit()
		editor.putBoolean(Constants.General.SHOW_GUIDE, true)
		editor.apply()
	}

	fun isShowGuide(): Boolean {
		return keyStore.getBoolean(Constants.General.SHOW_GUIDE, false)
	}
}