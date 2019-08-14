package com.randomusers

import com.domain.scheduler.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import java.util.concurrent.TimeUnit

/**
 * Default to Schedulers.trampoline() unless a TestScheduler has been provided
 */

class TestSchedulerProviderImpl(
    private val testScheduler: TestScheduler? = null
) : SchedulerProvider {

    override fun computation(): Scheduler = testScheduler ?: Schedulers.trampoline()

    override fun io(): Scheduler = testScheduler ?: Schedulers.trampoline()

    override fun mainThread(): Scheduler = testScheduler ?: Schedulers.trampoline()

    fun triggerActions() {
        testScheduler?.triggerActions()
    }

    fun advanceTestSchedulerTimeBy(delayTime: Long, unit: TimeUnit) {
        testScheduler?.advanceTimeBy(delayTime, unit)
    }

    fun advanceTestSchedulerTimeTo(delayTime: Long, unit: TimeUnit) {
        testScheduler?.advanceTimeTo(delayTime, unit)
    }
}