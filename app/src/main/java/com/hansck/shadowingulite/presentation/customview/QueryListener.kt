package com.hansck.shadowingulite.presentation.customview

import com.hansck.shadowingulite.database.QueryEnum

/**
 * Created by Hans CK on 13-Jun-18.
 */
interface QueryListener {
	fun onQuerySucceed(route: QueryEnum)

	fun onQueryFailed(route: QueryEnum, throwable: Throwable)
}