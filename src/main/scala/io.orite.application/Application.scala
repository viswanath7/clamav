package io.orite
package application
import zio.*
 
object Application extends zio.App: 

    val program = 
        for
            _ <- console.putStrLn("Hello from ZIO with Scala3!")
        yield ()

    override def run(args: List[String]) = program.exitCode