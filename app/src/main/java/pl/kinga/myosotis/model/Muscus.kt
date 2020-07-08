package pl.kinga.myosotis.model

enum class Muscus(
    val printName:String
) {
    LACK("Lack"),
    VERY_WATERY("Very watery"),
    WATERY("Watery"),
    DENSE("Dense"),
    VERY_DENSE("Very dense");

    companion object {
        fun fromString(string: String): Muscus {
            val enumVal = Muscus.values().first() { it.printName == string }
            return enumVal
        }
    }
}