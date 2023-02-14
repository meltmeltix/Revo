package com.alessiocameroni.revomusicplayer.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

internal fun horEnterScreenTransition(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { -100 },
        animationSpec = tween( 210 )
    ) + fadeIn(animationSpec = tween( 210 ))
}

internal fun horExitScreenTransition(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { -100 },
        animationSpec = tween( 210 )
    ) + fadeOut(animationSpec = tween( 210 ))
}

internal fun horEnterPlayerTransition(): EnterTransition {
    return slideInVertically(
        initialOffsetY = { -200 },
        animationSpec = tween( 240 )
    ) + fadeIn(animationSpec = tween( 260 ))
}

internal fun horExitPlayerTransition(): ExitTransition {
    return slideOutVertically(
        targetOffsetY = { -200 },
        animationSpec = tween( 240 )
    ) + fadeOut(animationSpec = tween( 260 ))
}

internal fun verEnterFragmentTransition(): EnterTransition {
    return slideInVertically (
        initialOffsetY = { 30 },
        animationSpec = tween( 210 )
    ) + fadeIn(animationSpec = tween( 210 ))
}

internal fun verExitFragmentTransition(): ExitTransition {
    return slideOutVertically(
        targetOffsetY = { -30 },
        animationSpec = tween(210)
    ) + fadeOut(animationSpec = tween(210))
}