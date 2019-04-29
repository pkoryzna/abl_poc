package abl


import scala.reflect.ClassTag

class ArrayBackedLookup[K <: Object : Ordering : ClassTag, V: ClassTag] private(elems: Seq[(K, V)]) {
  private val (keys, values) = {
    val (k, v) = elems.sortBy(_._1).unzip
    k.toArray -> v.toArray
  }

  def get(k: K): Option[V] = {
    val idx = java.util.Arrays.binarySearch(keys, k, implicitly[Ordering[K]])
    idx match {
      case i if i == keys.length => None
      case i if i < 0 => None
      case i => Some(values(i))
    }
  }

  def iterator: Iterator[(K, V)] = keys.iterator.zip(values.iterator)

}

object ArrayBackedLookup {
  def apply[K <: Object : Ordering : ClassTag, V: ClassTag]
  (elems: Seq[(K, V)]): ArrayBackedLookup[K, V] = new ArrayBackedLookup(elems)
}
