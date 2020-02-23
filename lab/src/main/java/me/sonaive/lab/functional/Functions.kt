package me.sonaive.lab.functional

typealias Supplier<T> = () -> T

interface Consumer<T> {

    fun accept(t: T)
}