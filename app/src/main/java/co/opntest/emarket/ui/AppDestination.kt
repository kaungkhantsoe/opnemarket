package co.opntest.emarket.ui

import co.opntest.emarket.ui.base.BaseAppDestination

sealed class AppDestination {

    object RootNavGraph : BaseAppDestination("rootNavGraph")

    object MainNavGraph : BaseAppDestination("mainNavGraph")
}
