package me.sonaive.lab.base.viewstate

/**
 * Created by liutao on 2020-03-04.
 * Describe: 抽象视图状态
 */

data class BaseViewState<T>(val isLoading: Boolean,
                         val throwable: Throwable?,
                         val result: T?) {

    companion object {
        fun <T> initial(): BaseViewState<T> {
            return BaseViewState(
                isLoading = false,
                throwable = null,
                result = null
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseViewState<*>

        if (isLoading != other.isLoading) return false
        if (throwable != other.throwable) return false
        if (result != other.result) return false

        return true
    }

    override fun hashCode(): Int {
        var res = isLoading.hashCode()
        res = 31 * res + (throwable?.hashCode() ?: 0)
        res = 31 * res + (result?.hashCode() ?: 0)
        return res
    }

}
