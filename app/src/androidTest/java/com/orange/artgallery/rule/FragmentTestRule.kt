package com.orange.artgallery.rule

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.orange.artgallery.KoinTestApp
import org.koin.core.module.Module

abstract class FragmentTestRule : ActivityTestRule<FragmentActivity>(
    FragmentActivity::class.java
) {
    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()

        val application = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as KoinTestApp
        application.injectModule(getModule())
    }

    override fun afterActivityLaunched() {
        super.afterActivityLaunched()

        activity.supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, createFragment())
            .commit()
    }

    protected abstract fun createFragment(): Fragment

    protected abstract fun getModule(): Module
}

fun createRule(fragment: Fragment, module: Module) = object : FragmentTestRule() {
    override fun createFragment() = fragment
    override fun getModule() = module
}
