package xyz.madki.ubiquity

/**
 * Created by madki on 13/06/18.
 */
class Database() {
    private val db: MutableMap<String, String> = HashMap()
    private var transactions: MutableList<Transaction> = ArrayList()
    private var currentTransaction: Transaction? = null

    fun beginTransaction() {
        currentTransaction = Transaction()
        transactions.add(currentTransaction!!)
    }

    fun commit() {
        if (hasTransactions()) {
            for (transaction in transactions) {
                transaction.commit()
            }
        }
        transactions = ArrayList()
        currentTransaction = null
    }

    fun rollback() {
        when {
            transactions.size == 0 -> return
            transactions.size == 1 -> {
                currentTransaction = null
                transactions.removeAt(0)
            }
            else -> {
                currentTransaction = transactions[transactions.size - 2]
                transactions.removeAt(transactions.size - 1)
            }
        }
    }

    fun put(key: String, value: String) {
        if (currentTransaction == null) {
            db.put(key, value)
        } else {
            currentTransaction!!.put(key, value)
        }
    }

    fun delete(key: String) {
        if (currentTransaction == null) {
            db.remove(key)
        } else {
            currentTransaction!!.delete(key)
        }
    }

    fun get(key: String): String? {
        if (hasTransactions()) {
            var result: Result
            for (i in transactions.size - 1 downTo 0) {
                result = transactions[i].get(key)
                if (result.isDeleted) {
                    return null
                } else if (result.hasValue()) {
                    return result.value
                }
            }
        }
        return db[key]
    }

    private fun hasTransactions(): Boolean {
        return transactions.size > 0
    }

    private fun Transaction.commit() {
        for (e in this.values.entries) {
            db.put(e.key, e.value)
        }

        for (s in this.deletedKeys) {
            db.remove(s)
        }
    }

    fun printDb() {
        println("#################")
        for (e in db.entries) {
            println("${e.key} : ${e.value}")
        }
        println("#################")
    }
}