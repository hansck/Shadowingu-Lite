package com.hansck.shadowingulite.screen.login

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.hansck.shadowingulite.model.LeaderboardUser
import com.hansck.shadowingulite.util.AuthManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class LoginViewModel(var context: Context?) {

	lateinit var account: GoogleSignInAccount

	fun setAcct() {
		account = AuthManager.instance.account
	}

	fun getLeaderboardUser(): LeaderboardUser {
		return LeaderboardUser(account.email!!, account.displayName!!, 1, "ic_avatar_a", ArrayList())
	}
}