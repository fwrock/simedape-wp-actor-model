package org.simedape.wp.util

import org.apache.spark.sql.types.{DoubleType, StructField, StructType}

import scala.io.Source
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

import java.io.StringReader

object TimeSeriesLoader {

  def loadTimeSeriesFromCSVFile(filePath: String): Array[Array[Double]] = {
    val bufferedSource = Source.fromFile(filePath)
    val timeSeries = bufferedSource.getLines().map { line =>
      line.split(";").map(_.trim.toDouble)
    }.toArray
    bufferedSource.close
    timeSeries
  }

  def  loadTimeSeriesDatasetFromCSVFile(filePath: String): Dataset[Array[Double]] = {
    val spark = SparkSession.builder()
      .appName("TimeSeriesAnalysis")
      .master("local[*]")
      .getOrCreate()

    val dataFrame = spark.read
      .format("csv")
      .option("header", "false")
      .option("delimiter", ";")
      .load(filePath)

    import spark.implicits._

    val timeSeries = dataFrame.as[Array[Double]]

    dataFrame.printSchema()
    dataFrame.show()
    spark.stop()

    timeSeries
  }

  def loadTimeSeriesDatasetFromCSVFile(amount: Int, timeSeriesSize: Int): Dataset[Array[Double]] = {
    val spark = SparkSession.builder()
      .appName("MatrixToDataFrame")
      .master("local[*]")
      .getOrCreate()

    // Suponha que você tenha uma matriz de dados
    val matrix: Array[Array[Double]] = Generators.generateTimeSeriesDataset(amount, timeSeriesSize)

    // Define o esquema para o DataFrame
    val schema = StructType(
      (0 until matrix(0).length).map(i => StructField(s"col_$i", DoubleType, nullable = false))
    )

    // Cria um DataFrame a partir da matriz usando o esquema definido
    val rows = matrix.map(row => Row.fromSeq(row.toSeq))
    val dataFrame: DataFrame = spark.createDataFrame(spark.sparkContext.parallelize(rows), schema)

    import spark.implicits._
    val timeSeries = dataFrame.as[Array[Double]]

    dataFrame.show()

    spark.stop()

    timeSeries
  }


}
