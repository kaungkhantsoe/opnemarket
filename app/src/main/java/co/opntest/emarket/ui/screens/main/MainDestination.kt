package co.opntest.emarket.ui.screens.main

import co.opntest.emarket.ui.base.BaseAppDestination

sealed class MainDestination {

    object Home : BaseAppDestination("home")

    object Summary : BaseAppDestination("summary")
}
