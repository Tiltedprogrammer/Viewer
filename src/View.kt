/**
 * Created by Holden Caulfield on 04.05.2017.
 */
import javax.swing.JFrame


class View(path: String):ViewInterface, JFrame() {
    override var model: ModelInterface? = null
    override var controller: ControllerInterface? = null
    override var painter: Painter? = null

    init {
        if (path.substring(path.lastIndexOf('.')) == ".bmp" || path.substring(path.lastIndexOf('.')) == ".dib") {
            controller = BMPController(this)
            controller!!.setData(path)
        } else {
            println("Unsupported extension")
        }
    }




    override fun update(model: ModelInterface) {


        when (model) {
            is BMP8Model ->
                painter = BMP8Drawer(model)

            is BMP24Model ->
                painter = BMP24Drawer(model)

            else ->
                println("Unsupported format")
        }

        val frame = JFrame("Name")
        frame.defaultCloseOperation=JFrame.EXIT_ON_CLOSE
        frame.setSize(model.width, Math.abs(model.height))
        frame.add(painter)
        frame.isVisible = true

    }
}





