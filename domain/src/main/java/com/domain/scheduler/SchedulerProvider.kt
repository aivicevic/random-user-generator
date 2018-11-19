package com.domain.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider {

    fun computation(): Scheduler {
        return Schedulers.computation()
    }

    fun io(): Scheduler {
        return Schedulers.io()
    }

    fun mainThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    fun newThread(): Scheduler {
        return Schedulers.newThread()
    }
}