import java.io.DataInputStream
import java.io.EOFException
import java.io.FileInputStream
import java.io.InputStream
import java.io.IOException



/**
 * Created by Holden Caulfield on 01.05.2017.
 */
interface Read{
    //var In = DataInputStream(streamIn)
    fun readint16(In: DataInputStream): Int{
        var short = In.readShort().toInt()
        return (short and 0xFF shl 8) or (short and 0xFF00 ushr 8)


    }
    fun readint32(In: DataInputStream):Int{
        return Integer.reverseBytes(In.readInt())
    }
    fun skip(In: DataInputStream, len: Long) {
        var interval = len
        while(interval > 0){
            var temp: Long = In.skip(interval)
            if(temp == 0.toLong()){
                throw EOFException()
            }
            interval -= temp
        }
    }

    //@Throws(IOException::class)
    fun readFully(In: DataInputStream, b: ByteArray) {
        var off = 0
        while (off < b.size) {
            val temp = In.read(b, off, b.size - off)
            if (temp == -1)
                throw EOFException()
            off += temp
        }
    }
    fun getValue(data: ByteArray?, startIndex: Int, length: Int) : Int{
        var value = 0
        var order = 1

        for (shift in 0..length - 1) {
            var byte : Int = data!![startIndex + shift].toInt()
            if (byte < 0)
                byte += 256
            value += ( byte * order )
            order *= 256
        }

        return value
    }

}