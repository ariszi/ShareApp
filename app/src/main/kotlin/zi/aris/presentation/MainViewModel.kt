package zi.aris.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userNavChooserUsecase: UserNavigationChooserUsecase) : ViewModel() {

    private val loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val navigation: MutableStateFlow<MainScreenContract.UserNavOptions> =
        MutableStateFlow(MainScreenContract.UserNavOptions.Idle)

    private val navChooser =
        navigation.filterNotNull().distinctUntilChanged().map { userNavChooserUsecase.invoke() }


    val state: StateFlow<MainScreenContract.MainScreenState> =
        combine(loading, navigation) { loading, navigation ->
            MainScreenContract.MainScreenState(
                loading,
                navigation
            )
        }.flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = MainScreenContract.MainScreenState()
            )

    private fun navigateUser() {
        viewModelScope.launch { navigation.update { navChooser.first() } }
    }

    fun consumeEvent(event: MainScreenContract.MainScreenEvent) {
        when (event) {
            is MainScreenContract.MainScreenEvent.UserLaunchedApp -> {
                navigateUser()
            }
        }
    }

}
