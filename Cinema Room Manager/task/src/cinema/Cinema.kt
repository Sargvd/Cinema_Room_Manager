package cinema

fun renderCinema(room: Array<CharArray>) {
    println("Cinema:")
    var header = " "
    for (i in 0..room[0].lastIndex) header += " ${i + 1}"
    println(header)
    for (i in 0..room.lastIndex) println("${i + 1} ${room[i].joinToString(" ")}")
}

fun bookSeat(room: Array<CharArray>, stats: Array<Int>) {
    while (true) {
        val seat = arrayOf(0, 0)
        println("Enter a row number:")
        print("> ")
        seat[0] = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        print("> ")
        seat[1] = readLine()!!.toInt()

        var price = if (seat[0] <= room.size/2) 10 else 8
        if (room.size * room[0].size <= 60) price = 10
        println("Ticket price: $$price")
        try {
            if (room[seat[0] - 1][seat[1] - 1] == 'S') {
                room[seat[0] - 1][seat[1] - 1] = 'B'
                stats[0]++
                stats[1] += price
                break
            } else println("That ticket has already been purchased!")
        }
        catch (e: ArrayIndexOutOfBoundsException) {
            println("Wrong input!")
        }
    }
}

fun maxIncome(room: Array<CharArray>): Int {
    var sum = 0
    val r = room.size
    val s = room[0].size
    if (r*s <= 60) sum = 10*r*s
    else sum = 10*s*(r/2) + 8*s*(r-r/2)
    return sum
}

fun createRoom(dimensions: List<Int>): Array<CharArray> {
    val (r,s) = dimensions
    val room = Array(r, { CharArray(s)})
    for (i in 0..room.lastIndex) {
        for (j in 0..room[i].lastIndex) room[i][j] = 'S'
    }
    return room
}

fun getDimensions(): List<Int> {
    println("Enter the number of rows:")
    print("> ")
    val r = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    print("> ")
    val s = readLine()!!.toInt()
    return listOf(r,s)
}

fun renderMenu(): Int {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    return readLine()!!.toInt()
}

fun renderStats(stats: Array<Int>, room: Array<CharArray>) {
    val numOfSeats = room.size*room[0].size
    println("Number of purchased tickets: ${stats[0]}")
    println("Percentage: ${String.format("%.2f",stats[0].toDouble()/numOfSeats*100)}%")
    println("Current income: $${stats[1]}")
    println("Total income: $${maxIncome(room)}")
}

fun main() {
    val stats = arrayOf(0, 0)
    val room = createRoom(getDimensions())
    while(true) {
        when (renderMenu()) {
            1 -> renderCinema(room)
            2 -> bookSeat(room, stats)
            3 -> renderStats(stats, room)
            0 -> break
            else -> continue
        }
    }
}