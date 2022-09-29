package zi.aris.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userNavChooserUsecase: UserNavigationChooserUsecase) : ViewModel() {

    private val navigation: MutableStateFlow<MainScreenContract.MainScreenState> =
        MutableStateFlow(MainScreenContract.MainScreenState())

    private val navChooser =
        navigation.filterNotNull().distinctUntilChanged().map { userNavChooserUsecase.invoke() }

    val state: StateFlow<MainScreenContract.MainScreenState> =
        navigation.asStateFlow()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = MainScreenContract.MainScreenState()
            )


    private fun navigateUser() {
        viewModelScope.launch { navigation.update { MainScreenContract.MainScreenState(navChooser.first()) } }
    }

    fun consumeEvent(event: MainScreenContract.MainScreenEvent) {
        when (event) {
            is MainScreenContract.MainScreenEvent.UserLaunchedApp -> {
                navigateUser()
            }
        }
    }

}
