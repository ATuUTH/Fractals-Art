package org.example.TreeStepbystep

import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Stage
import javafx.util.Duration
import kotlin.math.*

var currentDepth = 0
val maxDepth = 12
var time = 0.0

class GrowingShakingTree : Application() {
    override fun start(stage: Stage) {
        val canvas = Canvas(1000.0, 800.0)
        val gc = canvas.graphicsContext2D
        val root = StackPane(canvas)
        val scene = Scene(root, 1000.0, 800.0)
        stage.title = "Fractal Tree - Grow & Shake with Annotations"
        stage.scene = scene
        stage.show()

        gc.font = Font.font(10.0)

        val timeline = Timeline(
            KeyFrame(Duration.millis(50.0), {
                time += 0.05
                drawTree(gc)
            })
        )
        timeline.cycleCount = Timeline.INDEFINITE
        timeline.play()

        canvas.setOnMouseClicked {
            if (currentDepth < maxDepth) {
                currentDepth++
            }
        }
    }
}

fun main() {
    Application.launch(GrowingShakingTree::class.java)
}

fun drawTree(gc: GraphicsContext) {
    gc.clearRect(0.0, 0.0, 1000.0, 800.0)
    drawBranch(
        gc,
        x = 500.0,
        y = 750.0,
        angle = -90.0,
        length = 150.0,
        depth = 0,
        branchWidth = 16.0
    )
}

fun drawBranch(
    gc: GraphicsContext,
    x: Double,
    y: Double,
    angle: Double,
    length: Double,
    depth: Int,
    branchWidth: Double
) {
    if (depth > currentDepth || length < 2) return

    val shake = sin(time + depth) * (6.0 / (depth + 1))
    val rad = Math.toRadians(angle + shake)
    val x2 = x + cos(rad) * length
    val y2 = y + sin(rad) * length

    gc.lineWidth = branchWidth
    val hue = 30.0 + (90.0 - 30.0) * (depth.toDouble() / maxDepth)
    val brightness = 0.6 + 0.4 * (1 - depth.toDouble() / maxDepth)
    gc.stroke = Color.hsb(hue, 0.8, brightness)
    gc.strokeLine(x, y, x2, y2)

    // Ghi chú tại đầu nhánh
    gc.fill = Color.DARKBLUE
    gc.fillText("(${x.toInt()}, ${y.toInt()})", x + 4, y - 4)

    // Ghi chú tại cuối nhánh với độ dài
    gc.fill = Color.FIREBRICK
    gc.fillText("(${x2.toInt()}, ${y2.toInt()})", x2 + 4, y2 + 4)
    gc.fillText("len: ${"%.1f".format(length)}", x2 + 4, y2 + 16)

    val nextLength = length * 0.75
    val nextWidth = branchWidth * 0.7

    for (i in 0 until 2) {
        val offset = if (i == 0) -25.0 else 25.0
        drawBranch(gc, x2, y2, angle + offset, nextLength, depth + 1, nextWidth)
    }
}