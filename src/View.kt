/**
 * Created by Holden Caulfield on 04.05.2017.
 */
import javax.swing.JFrame


class View(path: String):ViewInterface, JFrame() {
    override var model: ModelInterface? = null
    override var controller: ControllerInterface? = null
    override var painter: Painter? = null

    init {
        if (path.substring(path.lastIndexOf('.')) == ".bmp") {
            controller = BMPController(this)
            controller!!.setData(path)
        } else {
            println(">Whoops")
        }
    }




    override fun update(model: ModelInterface) {

        when (model) {
            is BMP8Model ->
                painter = BMP8Drawer(model)

            is BMP24Model ->
                painter = BMP24Drawer(model)

            else ->
                println(">Whoops")
        }

        val frame = JFrame("Pisos")
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        frame.setSize(model.width, model.height)
        frame.add(painter)
        frame.isVisible = true

    }
}





