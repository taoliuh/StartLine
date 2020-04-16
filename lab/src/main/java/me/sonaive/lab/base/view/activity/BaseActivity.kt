package me.sonaive.lab.base.view.activity

import android.os.Bundle

abstract class BaseActivity : AutoDisposeActivity() {

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        binds()
    }

    open fun binds() {

    }

}