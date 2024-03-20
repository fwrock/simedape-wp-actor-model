package org.simedape.wp.util

import org.apache.spark.sql.SparkSession
import org.simedape.wp.dto.WarpingPathDTO

object WarpingPathWriter {

  def write(warpingPath: WarpingPathDTO, jsonFilePath: String): Unit = {
    val spark = SparkSession.builder()
      .appName("JsonSaveExample")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val warpingPaths = Seq(
      warpingPath
    ).toDF()

    warpingPaths.write
      .mode("append")
      .json(jsonFilePath)

    spark.stop()
  }
}
