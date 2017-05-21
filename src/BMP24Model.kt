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
    override var ClrUsed: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var observers: ArrayList<Observer> = ArrayList()
    override fun setImg(data: ByteArray ){
        val headerInfo = getData(data)?: return
        width = headerInfo.get("biWidth")!!
        height = headerInfo.get("biHeight")!!
        pixelStartIndex = getValue(data,0x0A, 4)
        if(headerInfo.get("biCompression") != 0){
            println("No compression supported")
            return
        }
        size = getValue(data,0x02,4)
        if(size == 0){
            println("size is 0")
            return
        }
        pixelArray = data.copyOfRange(pixelStartIndex, size)
        notifyObservers()

    }
    fun getData(data: ByteArray): HashMap<String, Int>?{
        var headerInfo = HashMap<String,Int>()
        val version = getValue(data,0x0E,2)
        when(version){
            12 -> {
                headerInfo.put("biWidth", getValue(data, 0x12,2))
                headerInfo.put("biHeight", getValue(data,0x14,2))
                if(headerInfo["biWidth"] == 0 || headerInfo["biHeigt"] == 0 ){
                    println("Unsupported data")
                    return null
                }

            }
            40 -> {
                headerInfo.put("biWidth", getValue(data,0x12,4))
                headerInfo.put("biHeight", getValue(data, 0x16, 4))
                headerInfo.put("biCompression", getValue(data,0x1E,4))
                headerInfo.put("biSize", getValue(data,0x22,4))

            }
            else -> {
                println("Unsupported format")
                return null
            }

        }
        return headerInfo
    }



}