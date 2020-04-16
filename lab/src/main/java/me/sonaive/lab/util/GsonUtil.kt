package me.sonaive.lab.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.Reader
import java.lang.reflect.Type


/**
 * Created by liutao on 2020-03-04.
 * Describe: gson工具类
 */
object GsonUtil {
    private val GSON = createGson(true)

    private val GSON_NO_NULLS = createGson(false)

    /**
     * Gets pre-configured [Gson] instance.
     *
     * @return [Gson] instance.
     */
    fun getGson(): Gson {
        return getGson(true)
    }

    /**
     * Gets pre-configured [Gson] instance.
     *
     * @param serializeNulls Determines if nulls will be serialized.
     * @return [Gson] instance.
     */
    fun getGson(serializeNulls: Boolean): Gson {
        return if (serializeNulls) GSON else GSON_NO_NULLS
    }

    /**
     * Serializes an object into json.
     *
     * @param obj The object to serialize.
     * @return object serialized into json.
     */
    fun toJson(obj: Any): String {
        return toJson(obj, true)
    }

    /**
     * Serializes an object into json.
     *
     * @param object       The object to serialize.
     * @param includeNulls Determines if nulls will be included.
     * @return object serialized into json.
     */
    fun toJson(`object`: Any, includeNulls: Boolean): String {
        return if (includeNulls) GSON.toJson(`object`) else GSON_NO_NULLS.toJson(`object`)
    }

    /**
     * Serializes an object into json.
     *
     * @param src       The object to serialize.
     * @param typeOfSrc The specific genericized type of src.
     * @return object serialized into json.
     */
    fun toJson(src: Any, typeOfSrc: Type): String {
        return toJson(src, typeOfSrc, true)
    }

    /**
     * Serializes an object into json.
     *
     * @param src          The object to serialize.
     * @param typeOfSrc    The specific genericized type of src.
     * @param includeNulls Determines if nulls will be included.
     * @return object serialized into json.
     */
    fun toJson(src: Any, typeOfSrc: Type, includeNulls: Boolean): String {
        return if (includeNulls) GSON.toJson(src, typeOfSrc) else GSON_NO_NULLS.toJson(
            src,
            typeOfSrc
        )
    }


    /**
     * Converts [String] to given type.
     *
     * @param json The json to convert.
     * @param type Type json will be converted to.
     * @return instance of type
     */
    fun <T> fromJson(json: String, type: Class<T>): T {
        return GSON.fromJson(json, type)
    }

    /**
     * Converts [String] to given type.
     *
     * @param json the json to convert.
     * @param type type type json will be converted to.
     * @return instance of type
     */
    fun <T> fromJson(json: String, type: Type): T {
        return GSON.fromJson(json, type)
    }

    /**
     * Converts [Reader] to given type.
     *
     * @param reader the reader to convert.
     * @param type   type type json will be converted to.
     * @return instance of type
     */
    fun <T> fromJson(reader: Reader, type: Class<T>): T {
        return GSON.fromJson(reader, type)
    }

    /**
     * Converts [Reader] to given type.
     *
     * @param reader the reader to convert.
     * @param type   type type json will be converted to.
     * @return instance of type
     */
    fun <T> fromJson(reader: Reader, type: Type): T {
        return GSON.fromJson(reader, type)
    }

    /**
     * Return the type of [List] with the `type`.
     *
     * @param type The type.
     * @return the type of [List] with the `type`
     */
    fun getListType(type: Type): Type {
        return TypeToken.getParameterized(List::class.java, type).type
    }

    /**
     * Return the type of [Set] with the `type`.
     *
     * @param type The type.
     * @return the type of [Set] with the `type`
     */
    fun getSetType(type: Type): Type {
        return TypeToken.getParameterized(Set::class.java, type).type
    }

    /**
     * Return the type of map with the `keyType` and `valueType`.
     *
     * @param keyType   The type of key.
     * @param valueType The type of value.
     * @return the type of map with the `keyType` and `valueType`
     */
    fun getMapType(keyType: Type, valueType: Type): Type {
        return TypeToken.getParameterized(Map::class.java, keyType, valueType).type
    }

    /**
     * Return the type of array with the `type`.
     *
     * @param type The type.
     * @return the type of map with the `type`
     */
    fun getArrayType(type: Type): Type {
        return TypeToken.getArray(type).type
    }

    /**
     * Create a pre-configured {@link Gson} instance.
     *
     * @param serializeNulls determines if nulls will be serialized.
     * @return {@link Gson} instance.
     */
    private fun createGson(serializeNulls: Boolean): Gson {
        val gsonBuilder = GsonBuilder()
        if (serializeNulls) gsonBuilder.serializeNulls()
        gsonBuilder.registerTypeAdapter(String::class.java, StringConverter())
        return gsonBuilder.create()
    }
}