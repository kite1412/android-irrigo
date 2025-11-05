package kite1412.irrigo.util

import kotlin.reflect.KClass

data class Destination(
    val route: KClass<*>,
    val iconId: Int,
    val name: String
)