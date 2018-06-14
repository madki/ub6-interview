package xyz.madki.ubiquity

/**
 * Created by madki on 13/06/18.
 */
fun main(args: Array<String>) {
    val db = Database()
    db.put("1", "hello")
    db.printDb()

    db.beginTransaction()
    db.put("2", "world")

    db.printDb()
    println("value at 2 is ${db.get("2")}")


    db.beginTransaction()
    db.put("1", "Not hello")
    println("value at 1 is ${db.get("1")}")
    db.rollback()
    println("value at 1 is ${db.get("1")}")

    db.commit()
    db.printDb()

    db.delete("1")
    db.printDb()
}


