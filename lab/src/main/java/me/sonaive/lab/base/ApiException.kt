package me.sonaive.lab.base

import java.lang.IllegalStateException

/**
 * Created by liutao on 2020-03-03.
 * Describe: api请求异常
 */
class ApiException(val code: Int, override val message: String): IllegalStateException(message)