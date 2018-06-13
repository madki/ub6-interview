package xyz.madki.ubiquity

/**
 * Created by madki on 13/06/18.
 */
data class Result(
        val isDeleted: Boolean = false,
        val value: String? = null
) {
    fun hasValue(): Boolean {
        return value != null
    }
}