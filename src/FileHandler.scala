import com.sun.net.httpserver.{HttpExchange, HttpHandler}
import java.io.{BufferedInputStream, FileInputStream}
import java.net.URI
import java.nio.file.Paths
import scala.util.{Try, Using, Failure}

object FileHandler {
  val URL_ROOT = new URI("/files")
  val Directory_ROOT = Paths.get("/Users/romancekobe/Desktop/MyFileServer/files")
  val NO_SUCH_FILE_RESPONSE = s"<h1>404 Not Found</h1><h1>File doesn't exist</h1>"
}

class FileHandler extends HttpHandler {
  override def handle(t: HttpExchange): Unit = {
    val requestURI = t.getRequestURI
    val responseHeaders = t.getResponseHeaders
    val responseOutputStream = t.getResponseBody

    val relativeFilePath = FileHandler.URL_ROOT.relativize(requestURI).toString
    val absoluteFilePath = FileHandler.Directory_ROOT.resolve(relativeFilePath).toString

    val serveResult: Try[Unit] = Using(new BufferedInputStream(new FileInputStream(absoluteFilePath))) {
      fileInputStream =>
        val fileName = getFileName(relativeFilePath)
        responseHeaders.add(s"Content-Disposition", s"attachment; filename=$fileName")
        t.sendResponseHeaders(200, fileInputStream.available())

        Iterator.continually(fileInputStream.read())
          .takeWhile(_ != -1)
          .foreach(responseOutputStream.write)
    }

    serveResult match {
      case Failure(e) =>
        t.sendResponseHeaders(404, FileHandler.NO_SUCH_FILE_RESPONSE.length)
        responseOutputStream.write(FileHandler.NO_SUCH_FILE_RESPONSE.getBytes)
        println(e.getMessage)
    }

    responseOutputStream.close()
  }

  def getFileName(filePath: String): String = {
    filePath.substring(filePath.lastIndexOf('/') + 1)
  }
}

