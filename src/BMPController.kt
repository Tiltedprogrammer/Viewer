import java.io.DataInputStream
import java.io.FileInputStream
import java.io.IOException

/**
 * Created by Holden Caulfield on 30.04.2017.
 */
class BMPController(override var view: ViewInterface, override var model: ModelInterface = BMP24Model()):ControllerInterface{
    override fun setData(path: String) {
        var streamIn : FileInputStream
        try {
            streamIn = FileInputStream(path)
        }
        catch (e : IOException) {
            println("file not found")
            return
        }

        var data : ByteArray = ByteArray(streamIn.available())
        streamIn.read(data)

        var file_extension = String(data.copyOfRange(0,2))

        if (file_extension != "BM" ){
            println("File extension is incorrect")
            return
        }
        when(getValue(data,0x0E,4)){
            12 ->{
                if (data[0x0A].toInt() == 24) {
                    model = BMP24Model()

                } else if (data[0x0A].toInt() == 8)
                    model = BMP8Model()

            }
            else -> {
                if (data[0x1C].toInt() == 24) {
                    model = BMP24Model()

                } else if (data[0x1C].toInt() == 8)
                    model = BMP8Model()

            }


        }
        model.registerObserver(view)
        model.setImg(data)


    }

}