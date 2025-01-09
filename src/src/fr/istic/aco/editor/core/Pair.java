package fr.istic.aco.editor.core;

/**
 * A generic Pair class that holds two related objects, one of type F (first) and the other of type S (second).
 * This class provides a simple way to store two objects together as a pair.
 *
 * @param <F> The type of the first object.
 * @param <S> The type of the second object.
 */
public class Pair<F, S> {
    private F first;
    private S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }


    public S getSecond() {
        return second;
    }

}