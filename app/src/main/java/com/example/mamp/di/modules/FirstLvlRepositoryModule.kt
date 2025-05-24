package com.example.mamp.di.modules

import com.example.mamp.data.repositoryImpl.FirstLvlNoteRepositoryImpl
import com.example.mamp.domain.repository.FirstLvlNoteRepository
import dagger.Module
import dagger.Provides

@Module
interface FirstLvlRepositoryModule {
    @Provides
    fun provideFirstLvlNoteRepository(impl: FirstLvlNoteRepositoryImpl): FirstLvlNoteRepository = impl
}