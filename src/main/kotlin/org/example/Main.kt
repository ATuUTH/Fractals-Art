package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.FlowPane
import javafx.scene.paint.Color
import javafx.stage.Stage

// Cấu hình chung
val ITER_MAX = 22
var iter = 1

class RotatedSquaresFractal : Application() {
    override fun start(stage: Stage) {
        val canvas = Canvas(600.0, 600.0)
        val gc = canvas.graphicsContext2D

        val rootNode = FlowPane()
        rootNode.alignment = Pos.CENTER
        rootNode.children.add(canvas)

        val scene = Scene(rootNode, 600.0, 600.0)
        stage.title = "Rotated Squares Fractal"
        stage.scene = scene
        stage.show()

        val x = 50.0
        val y = 50.0
        val s = 400.0
        val k = 0.15

        drawSquares(x, y, s, k, gc)
    }
}

fun main() {
    Application.launch(RotatedSquaresFractal::class.java)
}

// Hàm đệ quy vẽ các lớp fractal
fun drawSquares(_x: Double, _y: Double, _s: Double, k: Double, gc: GraphicsContext) {
    if (iter > ITER_MAX || _s <= 2.0) return

    val d = 0.5 * _s * k
    val s = _s - 2 * d
    val x = _x + d
    val y = _y + d

    val centerX = x + s / 2
    val centerY = y + s / 2

    // Vẽ 3 hình vuông với các góc xoay khác nhau
    drawSquare(gc, centerX, centerY, s, 0.0)
    drawSquare(gc, centerX, centerY, s, 30.0)
    drawSquare(gc, centerX, centerY, s, 60.0)

    iter += 1
    drawSquares(x, y, s, k, gc)
}

// Hàm vẽ 1 hình vuông xoay quanh tâm
fun drawSquare(gc: GraphicsContext, cx: Double, cy: Double, size: Double, angleDeg: Double) {
    gc.save()

    gc.translate(cx, cy)  // Dịch tới tâm hình vuông
    gc.rotate(angleDeg)   // Xoay quanh tâm theo góc độ

    // Vẽ hình vuông với gốc là tâm (0,0) đã xoay
    gc.stroke = Color.hsb(angleDeg * 3, 1.0, 0.8)
    gc.strokePolygon(
        doubleArrayOf(-size / 2, -size / 2, size / 2, size / 2),
        doubleArrayOf(-size / 2, size / 2, size / 2, -size / 2),
        4
    )

    gc.restore()  // Trả lại toạ độ gốc
}