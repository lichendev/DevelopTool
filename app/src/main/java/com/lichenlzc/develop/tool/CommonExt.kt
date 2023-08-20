package com.lichenlzc.develop.tool;

inline fun <reified T> Any?.castTo(): T?{
    return this as? T
}
