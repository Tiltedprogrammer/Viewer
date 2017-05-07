import java.io.DataInputStream
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage

/**
 * Created by Holden Caulfield on 01.05.2017.
 */
class BMP24Model: ModelInterface{
    override var height: Int = 0
    override var width: Int = 0
    override var size: Int = 0
    override var bytesForPixel: Int = 0
    override var pixelStartIndex: Int = 0
    override var countColorsInTable: Int = 0
    override var pixelArray: ByteArray? = null
    override var colorTable: ByteArray?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var CirUsed: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    //override var image: BufferedImage? = null
    var pixel: Int = 0
    override var observers: ArrayList<Observer> = ArrayList()
    override fun setImg(headerInfo: ByteArray, Stream: DataInputStream ){
        size = getValue(headerInfo,16,4)
        println(size)
        width = getValue(headerInfo,0,4)
        println(width)
        height = getValue(headerInfo,4,4)
        var WidthWithPad: Int = when ((width) % 4) {
            1 ->  + 3
            2 ->  + 2
            3 ->  + 1
            else -> 0
        }
        var realsize =(((width*3+WidthWithPad)* height))
        var tmpArray = ByteArray(realsize)
        readFully(Stream,tmpArray)
        pixelArray = tmpArray
        /**image = BufferedImage(width,height,1)
        for (i in height - 1 downTo 0) {

            for (j in 0..width - 1) {
                pixel = getValue(pixelArray, (height - 1 - i) * 3 * width + 3 * j, 3)
                image!!.setRGB(j, i, pixel)
            }
        }*/
        notifyObservers()

    }



}