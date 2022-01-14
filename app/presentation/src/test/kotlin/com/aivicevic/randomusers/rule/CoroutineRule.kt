package com.aivicevic.randomusers.rule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

annotation class CoroutineTest

class CoroutineRule : TestRule {

    val testCoroutineDispatcher = TestCoroutineDispatcher()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {

            override fun evaluate() {
                val enabled = description
                    .annotations
                    .filterIsInstance<CoroutineTest>()
                    .isNotEmpty()

                if (enabled) {
                    Dispatchers.setMain(testCoroutineDispatcher)
                }

                try {
                    base.evaluate()
                } finally {
                    if (enabled) {
                        Dispatchers.resetMain()
                        testCoroutineDispatcher.cleanupTestCoroutines()
                    }
                }
            }
        }
    }
}
