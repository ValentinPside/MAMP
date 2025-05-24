package com.example.mamp.di.components

import android.content.Context
import com.example.mamp.di.modules.DbModule
import com.example.mamp.di.modules.FirstLvlRepositoryModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DbModule::class,
        FirstLvlRepositoryModule::class
    ]
)
interface AppComponent {

    fun firstLvlNoteComponent(): FirstLvlNoteComponent

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}