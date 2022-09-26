package zi.aris.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zi.aris.useronboarding.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.main_activity) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerObservers()
    }


    private fun registerObservers() {
        lifecycleScope.launch { viewModel.state.collect { navigateUser(it.navChooser) } }
    }

    private fun navigateUser(navState: MainScreenContract.UserNavOptions) {

        val navId = when (navState) {
            is MainScreenContract.UserNavOptions.NavigateToPinSignIn -> zi.aris.pin.R.navigation.pin_nav_graph
            else -> zi.aris.onboarding.R.navigation.onboarding_nav_graph
        }

        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        hostFragment?.findNavController()?.setGraph(navId)
    }
}
