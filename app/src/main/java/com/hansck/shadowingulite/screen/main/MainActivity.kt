package com.hansck.shadowingulite.screen.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.auth.api.Auth
import com.hansck.shadowingulite.R
import com.hansck.shadowingulite.presentation.presenter.MainPresenter
import com.hansck.shadowingulite.presentation.presenter.MainPresenter.MainView.ViewState.*
import com.hansck.shadowingulite.screen.base.BaseActivity
import com.hansck.shadowingulite.screen.base.BaseFragment
import com.hansck.shadowingulite.screen.home.HomeFragment
import com.hansck.shadowingulite.screen.login.LoginActivity
import com.hansck.shadowingulite.util.AuthManager


class MainActivity : BaseActivity(), MainPresenter.MainView {

	private lateinit var model: MainViewModel
	private lateinit var presenter: MainPresenter
	lateinit var fm: FragmentManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		init()
		presenter.presentState(LOAD_LESSONS)
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		val inflater = menuInflater
		inflater.inflate(R.menu.main_menu, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		val id = item.itemId
		if (id == R.id.action_sign_out) {
			onSignOut()
			return true
		}
		return super.onOptionsItemSelected(item)
	}

	private fun init() {
		this.model = MainViewModel(this)
		this.presenter = MainPresenterImpl(this)
		fm = supportFragmentManager
	}

	override fun showState(viewState: MainPresenter.MainView.ViewState) {
		when (viewState) {
			IDLE -> showProgress(false)
			LOADING -> showProgress(true)
			LOAD_HOME -> loadHomeFragment()
			ERROR -> showError(null, getString(R.string.failed_request_general))
		}
	}

	override fun doRetrieveModel(): MainViewModel = this.model

	private fun loadHomeFragment() {
		val fragment: BaseFragment = HomeFragment()
		navigateTo(fm, fragment)
		presenter.presentState(IDLE)
	}

	private fun onSignOut() {
		AuthManager.instance.auth.signOut()
		Auth.GoogleSignInApi.signOut(AuthManager.instance.googleApiClient).setResultCallback {
			goToLogin()
		}
	}

	private fun goToLogin() {
		val intent = Intent(this, LoginActivity::class.java)
		startActivity(intent)
		finish()
	}
}
