package com.ecamarero.spacex.di

import com.ecamarero.spacex.domain.company.datasource.CompanyInfoDataSource
import com.ecamarero.spacex.network.company.CompanyInfoDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CompanyInfoDataSourceModule {

    @Binds
    internal abstract fun providesCompanyInfoDataSource(companyInfoDataSource: CompanyInfoDataSourceImpl): CompanyInfoDataSource
}