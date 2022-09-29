package zi.aris.main_screen

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import zi.aris.feature_shared.runTestWithDispatcher
import zi.aris.user.UserProfileContract
import zi.aris.user.UserProfileUsecase
import zi.aris.user.UserProfileViewModel

@ExperimentalCoroutinesApi
class UserProfileViewModelTest {

    private val savedStateMock: SavedStateHandle = SavedStateHandle()

    private val userInfo: UserProfileContract.UserData =
        UserProfileContract.UserData.UserInfo("Me", "MeToo", "Anderson@MeToo.co")
    private val userNavigation: UserProfileContract.UserNavigation =
        UserProfileContract.UserNavigation.Idle

    private lateinit var viewModelMock: UserProfileViewModel
    private var userProfileUsecaseMock: UserProfileUsecase = mockk {
        coEvery { this@mockk.getUserInfo() } returns userInfo
    }

    @Test
    fun `when user lands on the profile screen, then update UI with their info`() =
        runTestWithDispatcher {
            viewModelMock = UserProfileViewModel(savedStateMock, userProfileUsecaseMock)

            viewModelMock.state.test {
                viewModelMock.consumeEvent(UserProfileContract.UserProfileEvent.UserLandedOnProfile)

                skipItems(1)
                with(awaitItem()) {
                    displayUserData shouldBe userInfo
                    navigation shouldBe userNavigation
                }
            }
        }
}
