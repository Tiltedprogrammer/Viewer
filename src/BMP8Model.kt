import java.io.DataInputStream

/**
 * Created by Holden Caulfield on 07.05.2017.
 */
class BMP8Model: ModelInterface {
    override var height: Int = 0
    override var width: Int = 0
    override var size: Int = 0
    override var bytesForPixel: Int = 0
    override var pixelStartIndex: Int = 0
    override var countColorsInTable: Int = 0
    override var pixelArray: ByteArray? = null
    override var colorTable: ByteArray? = null
    override var CirUsed: Int = 0
    //override var image: BufferedImage? = null
    var pixel: Int = 0
    override var observers: ArrayList<Observer> = ArrayList()
    override fun setImg(headerInfo: ByteArray, Stream: DataInputStream) {
        size = getValue(headerInfo,16,4)
        width = getValue(headerInfo,0,4)
        height = getValue(headerInfo,4,4)
        var WidthWithPad: Int = when ((width) % 4) {
            1 ->  + 3
            2 ->  + 2
            3 ->  + 1
            else -> 0
        }
        CirUsed = getValue(headerInfo,28,4)
        var CirImportant = getValue(headerInfo,32,4)
        var tmpColorTable = ByteArray(256*4)
        readFully(Stream,tmpColorTable)
        colorTable = tmpColorTable
        var realsize =(((width+WidthWithPad)* height))
        pixelArray = ByteArray(realsize)
        readFully(Stream,pixelArray!!)
        notifyObservers()

    }
}