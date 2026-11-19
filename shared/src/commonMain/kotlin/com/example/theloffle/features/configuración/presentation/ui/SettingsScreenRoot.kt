package com.example.theloffle.features.configuración.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.theloffle.features.configuración.presentation.model.SettingsViewModel

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel,
    onBackClick: () -> Unit,
    onNavigateToPersonalData: () -> Unit,
    onLanguageClick: (String) -> Unit
) {
    SettingsScreen(
        backClick = onBackClick,
        onLogoutClick = {
            viewModel.logout()
        },
        showDataPersonal = onNavigateToPersonalData,
        changePassword = {},
        onLanguageClick = onLanguageClick,
        onAppearanceClick = {}
    )
}

@Composable
fun PersonalDataScreenRoot(
    viewModel: SettingsViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.personalDataUiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.loadPersonalData()
    }

    PersonalDataScreen(
        uiState = uiState,
        onBackClick = onBackClick
    )
}