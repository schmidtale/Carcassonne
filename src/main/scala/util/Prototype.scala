package util

trait Prototype[T] {
  def deepClone(): T
}
