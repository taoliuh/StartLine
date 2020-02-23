package me.sonaive.startline.ui.main

/**
 * Created by liutao on 2020-02-24.
 * Describe: 首页视图状态
 */

data class MainViewState(val isLoading: Boolean,
                         val throwable: Throwable?,
                         val result: String?) {

    companion object {
        fun initial(): MainViewState {
            return MainViewState(
                isLoading = false,
                throwable = null,
                result = null)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MainViewState

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
