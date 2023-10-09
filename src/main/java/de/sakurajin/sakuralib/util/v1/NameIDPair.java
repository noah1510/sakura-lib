package de.sakurajin.sakuralib.util.v1;

import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

/**
 * A pair the holds both the simple name and the identifier of a resource.
 */
public class NameIDPair extends Pair<String, Identifier> {
    /**
     * Creates a new NameIDPair with the given name and a given mod ID string.
     * @param name the name of the resource
     * @param modID the mod ID of the mod that holds the resource
     */
    public NameIDPair(String name, String modID) {
        super(name, new Identifier(modID, name));
    }

    /**
     * Creates a new NameIDPair with the given name and the given container.
     *
     * @param name      the name of the resource
     * @param container the container that holds the resource
     */
    public NameIDPair(String name, DatagenModContainer container) {
        super(name, container.getSimpleID(name));
    }

    /**
     * Creates a new NameIDPair with the given name, path and container.
     *
     * @param name      the name of the resource
     * @param path      the path of the resource
     * @param container the container that holds the resource
     * @see DatagenModContainer#getSimpleID(String, String) for more information on how the id is created
     */
    public NameIDPair(String name, String path, DatagenModContainer container) {
        super(name, container.getSimpleID(name, path));
    }

    /**
     * Return the name part of the pair.
     * A more readable version of getLeft()
     *
     * @return the name part of the pair
     */
    public String name() {
        return getLeft();
    }

    /**
     * Return the identifier part of the pair.
     * A more readable version of getRight()
     *
     * @return the identifier part of the pair
     */
    public Identifier ID() {
        return getRight();
    }

    /**
     * Return the identifier part of the pair as a string.
     * A more readable version of getRight().toString() or ID().toString()
     *
     * @return the identifier part of the pair as a string
     */
    public String IDString() {
        return ID().toString();
    }
}
