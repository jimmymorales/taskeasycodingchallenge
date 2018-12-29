import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.InvalidPathException

fun main(args: Array<String>) {

    // Checks that an argument is given
    if (args.isEmpty()) {
        return println(
            """
            |Missing file argument.
            |You need to pass a relative JSON file path.
            |Example: $ java -jar jvm-app/build/libs/jvm-app.jar input.json
            """.trimMargin()
        )
    }

    val fileName = args[0]

    val stringFile = readFile(fileName)
        // if string is null, print error message and return
        ?: return println(
            """
                            |Wrong path or invalid file - $fileName.
                            """.trimMargin()
        )

    println(stringFile)
}

/**
 * Reads file from system
 * @param fileName path to file
 * @return a string with the file text or null if file wasn't able t be read
 */
fun readFile(fileName: String): String? {
    return try {
        val path = FileSystems.getDefault().getPath(fileName)
        String(Files.readAllBytes(path))
    } catch (ipe: InvalidPathException) {
        null
    } catch (ioe: IOException) {
        null
    }
}