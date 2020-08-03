package phonebook

import java.io.File
import java.time.Duration
import java.time.Instant
import java.time.Instant.now

fun main() {
    // Load data
    val directory = File("D:/directory.txt").readLines()
    val names = File("D:/find.txt").readLines()
    // Start searching...
    val linearSearchDuration = runLinearSearch(names, directory)
    runBubbleSortAndJumpSearch(directory.toMutableList(), linearSearchDuration, names)
}

private fun runLinearSearch(names: List<String>, directory: List<String>): Duration {
    println("Start searching (linear search)...")
    val start = now()
    val foundCount = linearSearch(names, directory)
    val d = Duration.between(start, now())
    println("Found $foundCount / ${names.size} entries. Time taken: ${d.toTimeTaken()}")
    return d
}

private fun Duration.toTimeTaken(): String {
    return "${this.toMinutesPart()} min. ${this.toSecondsPart()} sec. ${this.toMillisPart()} ms."
}

private fun linearSearch(names: List<String>, directory: List<String>): Int {
    var foundCount = 0
    for (name in names) {
        for (line in directory) {
            if (line.endsWith(name)) {
                foundCount++
                break
            }
        }
    }
    return foundCount
}

private fun runBubbleSortAndJumpSearch(directory: MutableList<String>, linearSearchDuration: Duration, names: List<String>) {
    println("\nStart searching (bubble sort + jump search)...")
    val maxSortTime = linearSearchDuration.toMillis() * 10
    val start = now()
    bubbleSort(directory, start, maxSortTime)
    val startLinearSearch = now()
    linearSearch(names, directory)
    val d = Duration.between(startLinearSearch, now())
    println("Searching time: ${d.toTimeTaken()}")
}

private fun bubbleSort(directory: MutableList<String>, start: Instant, maxSortTime: Long) {
    var swap = true
    while (swap) {
        swap = false
        for (i in 0 until directory.lastIndex) {
            val name = directory[i].substringAfter(' ')
            val nextName = directory[i + 1].substringAfter(' ')
            if (name > nextName) {
                val temp = directory[i]
                directory[i] = directory[i + 1]
                directory[i + 1] = temp
                swap = true
            }
            if (i % 10000 == 0) {
                val d = Duration.between(start, now())
                if (d.toMillis() > maxSortTime) {
                    swap = false
                    println("Sorting time: ${d.toTimeTaken()} - STOPPED, moved to linear search")
                    break
                }
            }
        }
    }

}
