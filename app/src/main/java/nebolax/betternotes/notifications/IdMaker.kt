package nebolax.betternotes.notifications

object IdMaker {
    private var cur: Int = Int.MIN_VALUE
    fun getNext(): Int {
        return cur++
    }
}