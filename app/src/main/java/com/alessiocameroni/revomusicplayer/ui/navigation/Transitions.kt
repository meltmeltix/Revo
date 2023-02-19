package com.alessiocameroni.revomusicplayer.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

/**
 * Transitions used when navigating between screens.
 * - [horSlideEnterFromScreen] - Entering from screen | Screen(Destination) <- Current screen
 * - [horSlideExitToScreen] - Exiting to screen | Destination <- Screen(Current screen)
 * - [horSlidePopEnterFromScreen] - Pop entering from screen | Current screen -> Screen(Destination)
 * - [horSlidePopExitToScreen] - Pop exiting to screen | Screen(Current Screen) -> Destination
 *
 * - [verSlideEnterToPlayer] - Entering player | Screen(Destination) ^ Current screen
 * - [verSlideExitFromPlayer] - Exiting player | Current screen v Screen(Destination)
 * - [verSlidePopEnterFromPlayer] - Entering to player | Screen(Current screen) ^ Destination
 * - [verSlidePopExitFromPlayer] - Exiting from player | Current screen v Screen(Destination)
 *
 * Applied to:
 * - `welcome_screen`
 * - `main_screen`
 * - `player_screen`
 * - `search_screen` and relative sub-screens
 * - `settings_screen` and relative sub-screens
 */
internal fun horSlideEnterFromScreen(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { 100 },
        animationSpec = tween( 210 )
    ) + fadeIn(animationSpec = tween( 210 ))
}
internal fun horSlideExitToScreen(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { -100 },
        animationSpec = tween( 210 )
    ) + fadeOut(animationSpec = tween( 210 ))
}
internal fun horSlidePopEnterFromScreen(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { -100 },
        animationSpec = tween( 210 )
    ) + fadeIn(animationSpec = tween( 210 ))
}
internal fun horSlidePopExitToScreen(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { 100 },
        animationSpec = tween( 210 )
    ) + fadeOut(animationSpec = tween( 210 ))
}

internal fun verSlideEnterToPlayer(): EnterTransition {
    return slideInVertically(
        initialOffsetY = { 200 },
        animationSpec = tween( 240 )
    ) + fadeIn(animationSpec = tween( 260 ))
}
internal fun verSlideExitFromPlayer(): ExitTransition {
    return slideOutVertically(
        targetOffsetY = { 200 },
        animationSpec = tween( 240 )
    ) + fadeOut(animationSpec = tween( 260 ))
}
internal fun verSlidePopEnterFromPlayer(): EnterTransition {
    return slideInVertically(
        initialOffsetY = { -200 },
        animationSpec = tween( 240 )
    ) + fadeIn(animationSpec = tween( 260 ))
}
internal fun verSlidePopExitFromPlayer(): ExitTransition {
    return slideOutVertically(
        targetOffsetY = { -200 },
        animationSpec = tween( 240 )
    ) + fadeOut(animationSpec = tween( 260 ))
}


/**
 * Transition used when navigating between the main destinations
 * within the main screen.
 * [verSlideEnterFromFragment] and [fadeExitToFragment]
 * are applied to:
 * - `songs`
 * - `albums`
 * - `artists`
 * - `playlists`
 * - `spotify`
 *
 * Sub-screens listed below share [verSlideEnterFromFragment],
 * but they use [verSlideExitFromSubFragment] instead.
 * - `album_view_screen`
 * - `artist_view_screen`
 * - `playlist_view_screen`
 */
internal fun verSlideEnterFromFragment(): EnterTransition {
    return slideInVertically (
        initialOffsetY = { 30 },
        animationSpec = tween( 210 )
    ) + fadeIn(animationSpec = tween( 210 ))
}
internal fun fadeExitToFragment(): ExitTransition {
    return fadeOut(animationSpec = tween( 100 ))
}
internal fun verSlideExitFromSubFragment(): ExitTransition {
    return slideOutVertically(
        targetOffsetY = { -30 },
        animationSpec = tween(210)
    ) + fadeOut(animationSpec = tween(210))
}