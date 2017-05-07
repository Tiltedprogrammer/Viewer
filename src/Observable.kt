import java.awt.image.BufferedImage

/**
 * Created by Holden Caulfield on 04.05.2017.
 */
interface Observable {
    var observers: ArrayList<Observer>

    fun registerObserver (ob: Observer) {
        if (!observers.contains(ob))
            observers.add(ob)
    }

    fun removeObserver (ob: Observer) {
        observers.remove(ob)
    }

    fun notifyObservers()
}