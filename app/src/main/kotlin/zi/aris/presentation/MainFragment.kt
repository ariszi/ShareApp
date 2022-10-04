package zi.aris.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zi.aris.useronboarding.R
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var router: MainRouter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
        userLaunchedTheApp()
    }

    private fun userLaunchedTheApp() {
        viewModel.consumeEvent(MainScreenContract.MainScreenEvent.UserLaunchedApp)
    }

    private fun registerObservers() {
        lifecycleScope.launch { viewModel.state.collect { navigateUser(it.navChooser) } }
    }

    private fun navigateUser(navState: MainScreenContract.UserNavOptions) {
        when (navState) {
            is MainScreenContract.UserNavOptions.NavigateToPinSignIn ->
                router.navigateToPinLogin()
            is MainScreenContract.UserNavOptions.NavigateToOnboarding -> {
                router.navigateToOnBoarding()
            }

            else -> {}
        }


    }
}
