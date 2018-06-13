package xyz.madki.ubiquity

/**
 * Created by madki on 13/06/18.
 */
data class Transaction(
        val values: MutableMap<String, String> = HashMap(),
        val deletedKeys: MutableSet<String> = HashSet()
) {
    fun put(key: String, value: String) {
        if (deletedKeys.contains(key)) {
            deletedKeys.remove(key)
        }
        values.put(key, value)
    }

    fun delete(key: String) {
        values.remove(key)
        deletedKeys.add(key)
    }

    fun get(key: String): Result {
        return if (deletedKeys.contains(key)) {
            Result(true)
        } else {
            Result(false, values[key])
        }
    }
}

