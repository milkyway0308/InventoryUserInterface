package skywolf46.iui.kotlin.abstraction

interface IDataStore {
    fun has(str: String): Boolean
    fun <T : Any> get(str: String): T?
    fun <T : Any> set(str: String, data: T): T
    fun <T : Any> remove(str: String): T?
}