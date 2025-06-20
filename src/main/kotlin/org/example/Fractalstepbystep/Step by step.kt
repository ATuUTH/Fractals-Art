package org.example.Fractalstepbystep

import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.MouseEvent
import javafx.scene.layout.FlowPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Stage
import org.example.Fractal.GeometricFractal

var iter = 1
val ITER_MAX = 22
val xInit = 50.0
val yInit = 50.0
val sInit = 400.0
val k = 0.15

// Trạng thái thay đổi sau mỗi click
var currentX = xInit
var currentY = yInit
var currentS = sInit

class GeometricFractalInteractive : Application() {
    override fun start(stage: Stage) {
        val canvas = Canvas(600.0, 600.0)
        val gc = canvas.graphicsContext2D
        val rootNode = FlowPane()
        rootNode.alignment = Pos.CENTER
        rootNode.children.add(canvas)

        val scene = Scene(rootNode, 600.0, 600.0)
        stage.title = "Geometric Fractal with Annotations"
        stage.scene = scene
        stage.show()

        // Thiết lập font và vẽ hình đầu tiên
        gc.stroke = Color.BLACK
        gc.fill = Color.BLUE
        gc.font = Font("Arial", 12.0)
        drawAnnotatedSquare(gc, currentX, currentY, currentS)

        // Gán sự kiện click chuột
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED) {
            if (iter >= ITER_MAX) return@addEventHandler

            val d = 0.5 * currentS * k
            val newS = currentS - 2 * d
            val newX = currentX + d
            val newY = currentY + d

            drawAnnotatedSquare(gc, newX, newY, newS)

            // Cập nhật trạng thái
            currentX = newX
            currentY = newY
            currentS = newS
            iter += 1
        }
    }
}

fun drawAnnotatedSquare(gc: GraphicsContext, x: Double, y: Double, s: Double) {
    // Vẽ hình vuông
    gc.strokePolygon(
        doubleArrayOf(x, x, x + s, x + s),
        doubleArrayOf(y, y + s, y + s, y),
        4
    )

    // Ghi chú tọa độ các điểm
    gc.fillText("(${x.toInt()}, ${y.toInt()})", x + 5, y - 5)
    gc.fillText("(${(x + s).toInt()}, ${y.toInt()})", x + s - 60, y - 5)
    gc.fillText("(${x.toInt()}, ${(y + s).toInt()})", x + 5, y + s + 15)
    gc.fillText("(${(x + s).toInt()}, ${(y + s).toInt()})", x + s - 70, y + s + 15)

    // Ghi chú thông tin cạnh
    gc.fill = Color.WHITE
    gc.fillRect(x + s / 2 - 35, y + s / 2 - 8, 70.0, 16.0)

// Ghi text mới
    gc.fill = Color.BLUE
    gc.fillText("Cạnh: ${"%.2f".format(s)}", x + s / 2 - 30, y + s / 2)
}
fun main() {
    Application.launch(GeometricFractalInteractive::class.java)
}