import java.awt.Graphics
import java.awt.Color
/**
 * Created by Holden Caulfield on 07.05.2017.
 */
class BMP8Drawer (model: ModelInterface): Painter(model), Read {
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        if (g == null) return
        var WidthWithPad: Int = when ((model.width) % 4) {
            1 -> 3
            2 -> 2
            3 -> 1
            else -> 0
        }
        if (model.height > 0) {
            for (i in model.height - 1 downTo 0) {
                for (j in 0..model.width - 1) {

                    var col = model.pixelArray!![(model.height - 1 - i) * (model.width + WidthWithPad) + j].toInt()

                    if (col < 0) {
                        col += 256
                    }
                    col *= 4
                    var blue = model.colorTable!![col++].toInt()
                    if (blue < 0)
                        blue += 256

                    var green = model.colorTable!![col++].toInt()
                    if (green < 0)
                        green += 256

                    var red = model.colorTable!![col++].toInt()
                    if (red < 0)
                        red += 256

                    g.color = Color(red, green, blue)
                    g.drawLine(j, i, j, i)
                }
            }
        }
        else{
            for (i in 0 downTo Math.abs(model.height) - 1) {
                for (j in 0..model.width - 1) {

                    var col = model.pixelArray!![(i) * (model.width + WidthWithPad) + j].toInt()

                    if (col < 0) {
                        col += 256
                    }
                    col *= 4
                    var blue = model.colorTable!![col++].toInt()
                    if (blue < 0)
                        blue += 256

                    var green = model.colorTable!![col++].toInt()
                    if (green < 0)
                        green += 256

                    var red = model.colorTable!![col++].toInt()
                    if (red < 0)
                        red += 256

                    g.color = Color(red, green, blue)
                    g.drawLine(j, i, j, i)
                }
            }
        }
    }
}