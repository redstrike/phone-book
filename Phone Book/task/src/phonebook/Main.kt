package phonebook

import java.io.File
import java.time.Duration
import java.time.Instant.now

fun main() {
    // Load data into memory
    val directory = File("D:/directory.txt").readLines()
    val names = File("D:/find.txt").readLines()
    var foundCount = 0
    // Start linear search
    println("Start searching...")
    val start = now()
    for (name in names) {
        for (line in directory) {
            if (line.endsWith(name)) {
                foundCount++
                break
            }
        }
    }
    val end = now()
    // Calculate time taken
    val d = Duration.between(start, end)
    val timeTaken = "${d.toMinutesPart()} min. ${d.toSecondsPart()} sec. ${d.toMillisPart()} ms."
    println("Found $foundCount / ${names.size} entries. Time taken: $timeTaken")
}
