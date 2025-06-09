package com.example.mamp.di.components

import android.content.Context
import com.example.mamp.di.modules.DbModule
import com.example.mamp.di.modules.FirstLvlRepositoryModule
import com.example.mamp.di.modules.SecondLvlRepositoryModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DbModule::class,
        FirstLvlRepositoryModule::class,
        SecondLvlRepositoryModule::class
    ]
)
interface AppComponent {

    fun firstLvlNoteComponent(): FirstLvlNoteComponent

    fun secondLvlNoteComponent(): SecondLvlNoteComponent

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}