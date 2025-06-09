package com.example.mamp.di.modules

import com.example.mamp.data.repositoryImpl.SecondLvlNoteRepositoryImpl
import com.example.mamp.domain.repository.SecondLvlNoteRepository
import dagger.Module
import dagger.Provides

@Module
object SecondLvlRepositoryModule {
    @Provides
    fun provideSecondLvlNoteRepository(impl: SecondLvlNoteRepositoryImpl): SecondLvlNoteRepository =
        impl
}