import org.junit.Test
import javax.imageio.ImageIO.read
import org.junit.Assert.*
import java.awt.Color
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.Arrays.copyOf
import java.util.Arrays.parallelSetAll

/**
 * Created by Holden Caulfield on 12.05.2017.
 */
class BMPModelTest {
    fun getData(file_name: String): ByteArray? {
        val streamIn: FileInputStream
        try {
            streamIn = FileInputStream(file_name)
        } catch (e: IOException) {
            println("file not found")
            return null
        }
        val data: ByteArray = ByteArray(streamIn.available())
        streamIn.read(data)
        return data
    }
    fun getValue(data: ByteArray?, startIndex: Int, length: Int) : Int{
        var value = 0
        for (shift in 0..length - 1) {
            var byte : Int = data!![startIndex + shift].toInt()
            if (byte < 0)
                byte += 256
            value += byte.shl(shift*8)
        }

        return value
    }

    @Test
    fun bmp8Correctness() {
        val name = "2.bmp"
        val file = File(name)
        val actualImage = read(file)
        val model = BMP8Model()
        model.setImg(getData(name)!!)

        var WidthWithPad: Int = when ((model.width) % 4) {
            1 -> 3
            2 -> 2
            3 -> 1
            else -> 0
        }
        if(model.height > 0) {
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
                    if (Color(actualImage.getRGB(j, i)) != Color(red, green, blue)) {
                        assertEquals(1, 2)
                    }

                }

            }
        }
        else{
            for (i in  0 downTo Math.abs(model.height) - 1) {
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
                    if (Color(actualImage.getRGB(j, i)) != Color(red, green, blue)) {
                        assertEquals(1, 2)
                    }

                }

            }
        }
    }
    @Test
    fun bmp24Correctness() {
        val names = ArrayList<String>()
        names.add("1.bmp")
        for (filename in names) {
            val file = File(filename)
            val actualImage = read(file)
            val model = BMP24Model()
            model.setImg(getData(filename)!!)
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
                        if (Color(actualImage.getRGB(j, i)) != Color(rgb)) {
                            assertEquals(1, 2)
                        }
                    }
                }
            }
            else{
                for (i in 0 downTo Math.abs(model.height) -1) {
                    for (j in 0..model.width - 1) {
                        rgb = getValue(model.pixelArray, (i) * (model.width * 3 + WidthWithPad) + 3 * j, 3)
                        if (Color(actualImage.getRGB(j, i)) != Color(rgb)) {
                            assertEquals(1, 2)
                        }
                    }
                }
            }
        }

    }
}


