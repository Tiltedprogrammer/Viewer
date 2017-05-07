import java.io.DataInputStream
import java.io.FileInputStream
import java.io.IOException

/**
 * Created by Holden Caulfield on 30.04.2017.
 */
class BMPController(override var view: ViewInterface, override var model: ModelInterface = BMP24Model()):ControllerInterface{
    override fun setData(path: String) {
        var streamIn: FileInputStream
        try {
            streamIn = FileInputStream(path)
        } catch(e: IOException) {
            println("File not found")
            return
        }
        var DataInput = DataInputStream(streamIn)
        if (readint16(DataInput) == 0x4D42) {
            skip(DataInput, 8)
            var bfOffBits = readint32(DataInput)
            var bitCount = readint32(DataInput)
            var headerInfo = ByteArray(bitCount - 4)
            //println("width" + getValue(headerInfo,0,4))
            //println("height" + getValue(headerInfo,4,4))
            readFully(DataInput, headerInfo)
            if (getValue(headerInfo, 10, 2) == 24) {

                model = BMP24Model()

            } else if (getValue(headerInfo, 10, 2) == 8)
                model = BMP8Model()
            model.registerObserver(view)
            model.setImg(headerInfo, DataInput)


        } else {
            return
        }
    }
}