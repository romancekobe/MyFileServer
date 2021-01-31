# MyFileServer

Requirement: The exercise is to write a web server that serves files from a directory on the local file system where multiple clients will make requests at the same time. You can use your preference of Scala, Python, or Java. Please do not use a pre-existing framework, but feel free to use anything available in the standard language, e.g.  com.sun.net.httpserver.HttpServer from Java, etc. or start from scratch, as you prefer. Also, please consider your testing approach.

How to run my code:
1. First, we need to change the default root directory of our file system in FileHandler.scala (aka variable "Directory_ROOT").
2. Then, we can run the FileServer.scala to init our file server.
3. We can make a GET request to http://localhost:8000/files/{relativeFilePath} through the browser. If the file exists, the server will return the file in the response; Otherwise, the server will return a 404 response indicating the resource doesn't exist on the server.
