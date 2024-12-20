package carcassonne.util

trait Prototype[T] {
  def deepClone(): T
}
