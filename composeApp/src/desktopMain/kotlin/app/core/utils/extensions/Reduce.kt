package app.core.utils.extensions

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.reduce(transform: T.() -> T) {
    value = value.transform()
}
