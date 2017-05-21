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
    override var ClrUsed: Int = 0
    override var observers: ArrayList<Observer> = ArrayList()
    override fun setImg(data: ByteArray) {
        val headerInfo = getData(data)?: return
        size = getValue(data,0x02,4)
        if(size == 0){
            println("size is 0")
            return
        }
        width = headerInfo.get("biWidth")!!
        height = headerInfo.get("biHeight")!!
        pixelStartIndex = getValue(data, 0x0A,4)
        colorTable = data.copyOfRange((headerInfo.get("headerSize")!! + 14),pixelStartIndex-1)
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
                if(headerInfo["biWidth"] == 0 || headerInfo["biHeight"] == 0 ){
                    println("Unsupported data")
                    return null
                }

            }
            40 -> {
                headerInfo.put("headerSize",version)
                headerInfo.put("biWidth", getValue(data,0x12,4))
                headerInfo.put("biHeight", getValue(data, 0x16, 4))
                headerInfo.put("biCompression", getValue(data,0x1E,4))
                headerInfo.put("biSize", getValue(data,0x22,4))
                if(headerInfo["biWidth"] == 0 || headerInfo["biHeight"] == 0 ) {
                    println("Unsupported data")
                    return null
                }

            }
            108 ->{
                headerInfo.put("headerSize",version)
                headerInfo.put("biWidth", getValue(data,0x12,4))
                headerInfo.put("biHeight", getValue(data, 0x16, 4))
                headerInfo.put("biCompression", getValue(data,0x1E,4))
                headerInfo.put("biSize", getValue(data,0x22,4))
                println("kok")
                if(headerInfo["biWidth"] == 0 || headerInfo["biHeight"] == 0 ) {
                    println("Unsupported data")
                    return null
                }

            }
            else -> {
                println("Unsupported format")
                return null

            }

        }
        return headerInfo
    }
}