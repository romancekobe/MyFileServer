import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.util.concurrent.Executors

object FileServer {
  val PORT = 8000
  val MAX_BACKLOG = 100

  def main(args: Array[String]) {
    val server = HttpServer.create(new InetSocketAddress(PORT), MAX_BACKLOG)

    server.createContext("/files", new FileHandler)

    val exec = Executors.newCachedThreadPool()
    server.setExecutor(exec)

    server.start()
  }
}