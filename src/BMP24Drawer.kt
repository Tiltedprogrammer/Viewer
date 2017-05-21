/**
 * Created by Holden Caulfield on 04.05.2017.
 */
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.swing.JFrame

class BMP24Drawer(model: ModelInterface): Painter(model), Read {
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        if (g == null) return
        var rgb: Int
        var WidthWithPad: Int = when ((model.width) % 4) {
            1 -> +3
            2 -> +2
            3 -> +1
            else -> 0
        }
        if (model.height > 0) {
            for (i in model.height - 1 downTo 0) {

                for (j in 0..model.width - 1) {
                    rgb = getValue(model.pixelArray, (model.height - 1 - i) * (model.width * 3 + WidthWithPad) + 3 * j, 3)
                    g.color = Color(rgb)
                    g.drawLine(j, i, j, i)
                }
            }

        }
        else{
            for (i in 0 downTo Math.abs(model.height) - 1) {

                for (j in 0..model.width - 1) {
                    rgb = getValue(model.pixelArray, (i) * (model.width * 3 + WidthWithPad) + 3 * j, 3)
                    g.color = Color(rgb)
                    g.drawLine(j, i, j, i)
                }
            }
        }
    }
}