import akka.actor.ActorSystem


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn

/**
  * handle client requests as a RESTFUL server
  */
object WebServer {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("api-server")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val route=path("hello"){
      get{
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    }
    val bingdingFuture = Http().bindAndHandle(route,"localhost",8080)
    StdIn.readLine()
    bingdingFuture.flatMap(_.unbind()).onComplete(_=>system.terminate())
  }

}