package com.crisangel.recipesapp.commons.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

class TestCoroutineDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        block.run()
    }
}
