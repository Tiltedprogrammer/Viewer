/**
 * Created by Holden Caulfield on 04.05.2017.
 */
interface ViewInterface: Observer {
    var model : ModelInterface?
    var controller : ControllerInterface?
    var painter : Painter?

    //fun repaint()
}