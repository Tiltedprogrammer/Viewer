import java.awt.image.BufferedImage
import java.io.DataInputStream

/**
 * Created by Holden Caulfield on 04.05.2017.
 */
interface ModelInterface: Observable,Read {
    var width: Int
    var height : Int
    var size : Int
    var bytesForPixel : Int
    var pixelStartIndex : Int
    var countColorsInTable : Int
    var colorTable: ByteArray?
    var pixelArray: ByteArray?
    var ClrUsed: Int
    fun setImg(data: ByteArray)
    override fun notifyObservers() {
        for (ob in observers) {
            ob.update(this)
        }
    }
}