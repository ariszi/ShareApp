package zi.aris.presentation

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import zi.aris.login.LoginRouter
import zi.aris.onboarding.presentation.OnboardingRouter
import zi.aris.user.UserProfileRouter

@InstallIn(ActivityComponent::class)
@Module
class NavigatorModule {

    @ActivityScoped
    @Provides
    fun provideLoginRouterModule(@ActivityContext context: Context): LoginRouter {
        return Navigator(context)
    }

    @ActivityScoped
    @Provides
    fun provideUserProfileRouterModule(@ActivityContext context: Context): UserProfileRouter {
        return Navigator(context)
    }

    @ActivityScoped
    @Provides
    fun provideOnboardingRouterModule(@ActivityContext context: Context): OnboardingRouter {
        return Navigator(context)
    }
}
