package io

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class ApigadPing extends Simulation {

  val concurrentUsers = scala.util.Properties.envOrElse("IO_LOADTESTS_CONCURRENT_USERS", "100")
  
  val httpProtocol = http.baseUrl("https://api-gad.io.italia.it")

  val scn =
    scenario("apigad")
      .exec(
        http("/ping")
          .get("/ping")
          .check(status.is(200))
      )
      .pause(1)

  setUp(scn.inject(constantConcurrentUsers(concurrentUsers.toInt) during (60 seconds)).protocols(httpProtocol))

}