package com.randomusers.common

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class ViewLifecycleFragment : Fragment() {

    protected var viewLifecycleOwner: ViewLifecycleOwner? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner = ViewLifecycleOwner()
        viewLifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onStart() {
        super.onStart()
        viewLifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onPause() {
        viewLifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        super.onPause()
    }

    override fun onStop() {
        viewLifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        viewLifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        viewLifecycleOwner = null
        super.onDestroyView()
    }

    inner class ViewLifecycleOwner : LifecycleOwner {
        private val lifecycleRegistry = LifecycleRegistry(this)

        override fun getLifecycle() = lifecycleRegistry
    }
}
