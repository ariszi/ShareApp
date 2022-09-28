package zi.aris.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zi.aris.useronboarding.R

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels()

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
        val hostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        when (navState) {
            is MainScreenContract.UserNavOptions.NavigateToPinSignIn ->
                hostFragment?.findNavController()?.setGraph(zi.aris.pin.R.navigation.pin_nav_graph)
            is MainScreenContract.UserNavOptions.NavigateToOnboarding -> {
                hostFragment?.findNavController()?.setGraph(zi.aris.onboarding.R.navigation.onboarding_nav_graph)
            }

            else -> {}
        }


    }
}
