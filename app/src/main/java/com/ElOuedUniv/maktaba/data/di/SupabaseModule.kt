package com.ElOuedUniv.maktaba.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {
    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://emvzvqgbsugdbpqzdtog.supabase.co/rest/v1/",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVtdnp2cWdic3VnZGJwcXpkdG9nIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzgwMDI1ODEsImV4cCI6MjA5MzU3ODU4MX0.TOBsr_oiqasNOos06GJUC32TaOmcZFv4vcpzx1-ZXrg"
        ) {
            install(Postgrest)
            install(Storage)
        }
    }
}