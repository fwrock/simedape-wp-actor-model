package org.simedape.wp.util

import org.apache.spark.sql.SparkSession
import org.simedape.wp.dto.ClusteringDTO


object ClusteringLoader {

  def loadClusteringFromJsonFile(filePath: String): ClusteringDTO = {
    val spark = SparkSession.builder()
      .appName("JsonParsingExample")
      .master("local[*]")
      .getOrCreate()


    val dataFrame = spark.read
      .json(filePath)

    import spark.implicits._
    val clustering = dataFrame.as[ClusteringDTO]

    clustering.show()
    spark.stop()

    clustering.first()
  }

  def loadClusteringFromJsonFile(numberOfClusters: Int, timeSeriesSize: Int): ClusteringDTO = {
    val spark = SparkSession.builder()
      .appName("JsonParsingExample")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._
    val dataFrame = spark.read
      .json((spark.createDataset(Seq(Generators.generateClusteringStringJson(numberOfClusters, timeSeriesSize)))))
      .toDF()

    val clustering = dataFrame.as[ClusteringDTO]

    clustering.show()
    spark.stop()

    clustering.first()
  }



}
