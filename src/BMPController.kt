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
        var version = getValue(data,0x0E,4)
        when(version){
            12 ->{
                if (getValue(data,0x0A,2) == 24) {
                    model = BMP24Model()

                } else if (getValue(data,0x0A,2) == 8)
                    model = BMP8Model()
                else{
                    println("Unsupported bit per pixel")
                    return
                }

            }
            40,108,124 -> {
                if (getValue(data,0x1C,2) == 24) {
                    model = BMP24Model()

                } else if (getValue(data,0x1C,2) == 8 || getValue(data,0x1C,2) == 1)
                    model = BMP8Model()
                else{
                    println("Unsupported bits per pixel")
                    return
                }

            }
            else ->{
                println("Broken version")
                return
            }


        }
        model.registerObserver(view)
        model.setImg(data)


    }

}