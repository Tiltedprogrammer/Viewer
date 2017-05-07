/**
 * Created by Holden Caulfield on 04.05.2017.
 */
interface ControllerInterface: Read  {
    var model : ModelInterface
    var view : ViewInterface
    fun setData(path: String)

}