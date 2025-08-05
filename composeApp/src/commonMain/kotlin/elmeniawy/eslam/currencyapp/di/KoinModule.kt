package elmeniawy.eslam.currencyapp.di

import com.russhwolf.settings.Settings
import elmeniawy.eslam.currencyapp.data.local.PreferencesImpl
import elmeniawy.eslam.currencyapp.data.remote.api.CurrencyApiServiceImp
import elmeniawy.eslam.currencyapp.domain.CurrencyApiService
import elmeniawy.eslam.currencyapp.domain.PreferencesRepository
import elmeniawy.eslam.currencyapp.presentation.screen.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * KoinModule
 *
 * Created by Eslam El-Meniawy on 03-Aug-2025 at 3:10 PM.
 */

val appModule = module {
    single { Settings() }
    single<PreferencesRepository> { PreferencesImpl(get()) }
    single<CurrencyApiService> { CurrencyApiServiceImp(get()) }

    factory {
        HomeViewModel(get(), get())
    }
}

fun initializeKoin() {
    startKoin {
        modules(appModule)
    }
}