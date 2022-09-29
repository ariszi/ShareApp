package zi.aris.feature_shared

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*

/**
 * @param testDispatcher [TestDispatcher] to use for this test. [Dispatchers.Main] will be substituted with it.
 * @param test body of the test.
 * This should be in a test module instead of a shared feature module.
 * @see <a href="https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test">Test API reference</a>
 */
@ExperimentalCoroutinesApi
fun runTestWithDispatcher(
    testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
    test: suspend TestScope.() -> Unit,
): TestResult {
    Dispatchers.setMain(testDispatcher)

    return runTest {
        test()
        Dispatchers.resetMain()
    }
}
