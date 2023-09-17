package de.sakurajin.sakuralib.util.v1;

import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;

public class TagIdentifier extends Identifier {

    public TagIdentifier(String namespace, String path) {
        super(tagValidateNamespace(namespace, path), tagValidatePath(namespace,path), null);
    }

    protected TagIdentifier(String[] namespaceAndPath) {
        this(namespaceAndPath[0], namespaceAndPath[1]);
    }

    public TagIdentifier(String name) {
        this(tagSplit(name, "minecraft"));
    }

    public static TagIdentifier ofDefault(String path, String defaultNamespace) {
        return new TagIdentifier(tagSplit(path, defaultNamespace));
    }

    protected static String tagValidatePath(String namespace, String path) {
        if(!path.matches("^[a-z0-9/._-]+$")) {
            throw new InvalidIdentifierException("Non [a-z0-9/._-] character in path of location: " + namespace + ":" + path);
        }

        return path;
    }

    protected static String tagValidateNamespace(String namespace, String path) {
        if(!namespace.matches("^#?[a-z0-9_.-]+$")) {
            throw new InvalidIdentifierException("Non [a-z0-9#_.-] character in namespace of location: " + namespace + ":" + path);
        }
        return namespace;
    }

    protected static String[] tagSplit(String name, String defaultNamespace) {
        boolean isTag = name.startsWith("#");

        String[] splitName = name.split(":");
        if(splitName.length == 1) {
            splitName = new String[]{defaultNamespace, splitName[0]};
            if(isTag){
                splitName[0] = "#" + splitName[0];
                splitName[1] = splitName[1].substring(1);
            }
        }
        if(splitName.length != 2) {
            throw new InvalidIdentifierException("Not a valid tag identifier: " + name);
        }

        return splitName;
    }

}
