package abl

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.collection.immutable.TreeMap
import scala.util.Random

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Array(Mode.AverageTime))
class ABLBench {
  def randomElements() = Iterator
    .continually(Random.nextInt())
    .map(i => i.toString -> s"AAA$i")
    .take(1000000).toSeq

  @Benchmark
  def ablCreate(): Unit = {
    ArrayBackedLookup(randomElements())
  }

  @Benchmark
  def treeMapCreate(): Unit = {
    val builder = TreeMap.newBuilder[String, String]
    randomElements().foreach(builder += _)
    builder.result()

  }

}
