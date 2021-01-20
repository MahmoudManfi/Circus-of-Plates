package eg.edu.alexu.csd.oop.game.DynamicLinkage;

import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.Shapes.FlyWeight;
import eg.edu.alexu.csd.oop.game.Shapes.Shape;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DynamicLinkage {
    private static DynamicLinkage dynamicLinkage = null;
    private static String[] INVALID_CLASSES = {"Bar.class"};
    private static Logger logger = Logger.getLogger(DynamicLinkage.class);
    private static final Class ASSIGNED_INTERFACE = Shape.class;
    private DynamicLinkage() {
        BasicConfigurator.configure();
    }

    public static synchronized DynamicLinkage getInstance() {
        synchronized (DynamicLinkage.class) {
            if (dynamicLinkage == null)
                dynamicLinkage = new DynamicLinkage();
            return dynamicLinkage;
        }
    }

    private static boolean isInvalidClass(String path) {
        for (String invalidClasses : INVALID_CLASSES) {
            if (path.endsWith(invalidClasses))
                return true;
        }
        return false;
    }

    public List<Class<? extends Shape>> loadJars() {
        List<Class<? extends Shape>> classesLoaded = new ArrayList<>();
        String currentDir = System.getProperty("user.dir") + Game.JAR_PATH;
        File file = new File(currentDir);
        file.mkdir();
        if (Objects.requireNonNull(file.listFiles()).length == 0) {
            logger.fatal("No game objects found!!");
            logger.fatal("Please include jar in " + currentDir + " folder.");
            throw new RuntimeException();
        }
        for (final File fileEntry : Objects.requireNonNull(file.listFiles())) {
            if (fileEntry.isFile() && fileEntry.getAbsolutePath().contains(".jar")) {
                List<Class<? extends Shape>> curLoaded = loadJar(fileEntry);
                classesLoaded.addAll(curLoaded);
            }
        }
        if (classesLoaded.isEmpty())
        {
            logger.fatal("No classes loaded from jar!!");
            logger.fatal("Jar Path " + currentDir + "\n Does the jar exist??");
            throw new RuntimeException();
        }
        return classesLoaded;
    }

    private List<String> getClassesInJar(String jarPath) throws IOException {
        List<String> classNames = new ArrayList<String>();
        ZipInputStream zip = new ZipInputStream(new FileInputStream(jarPath));
        for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
            if (!entry.isDirectory() && isInvalidClass(entry.getName()))
                continue;
            if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                String className = entry.getName().replace('/', '.'); // including ".class"
                classNames.add(className.substring(0, className.length() - ".class".length()));
            }
        }
        return classNames;
    }

    private List<Class<? extends Shape>> loadJar(File jarFile) {
        List<Class<? extends Shape>> validClasses = new ArrayList<>();
        try {
            URLClassLoader child = new URLClassLoader(
                    new URL[]{jarFile.toURI().toURL()},
                    this.getClass().getClassLoader()
            );
            List<String> classes = getClassesInJar(jarFile.getAbsolutePath());
            for (String className : classes) {
                Class<?> currentClass = Class.forName(className, true, child);
                Class<?>[] interfaces = currentClass.getInterfaces();
                Constructor<?>[] constructors = currentClass.getConstructors();
                if (interfaces.length != 1) {
                    logger.warn("Failed to load class " + currentClass.getSimpleName() + ".");
                    logger.warn(currentClass.getSimpleName() + " must implement only 1 interface");
                    continue;
                }
                if (!interfaces[0].equals(ASSIGNED_INTERFACE)) {
                    logger.warn("Failed to load class " + currentClass.getSimpleName() + ".");
                    logger.warn(currentClass.getSimpleName() + " must implement only Shape interface");
                    continue;
                }
                if (constructors.length < 2) {
                    logger.warn("Failed to load class " + currentClass.getSimpleName() + ".");
                    logger.warn(currentClass.getSimpleName() + " must have 2 constructors");
                    continue;
                }
                if (constructors.length > 2) {
                    logger.warn("Failed to load class " + currentClass.getSimpleName() + ".");
                    logger.warn(currentClass.getSimpleName() + " must have only 2 constructors");
                    continue;
                }
                //swapping constructors so that constructor[0] is the valid constructor to call
                if (constructors[0].getParameterTypes().length < constructors[1].getParameterTypes().length) {
                    Constructor tmpConst = constructors[1];
                    constructors[1] = constructors[0];
                    constructors[0] = tmpConst;
                }
                Class<?>[] parametersType = constructors[0].getParameterTypes();
                if (!parametersType[0].equals(int.class) || !parametersType[1].equals(int.class) || !parametersType[2].equals(FlyWeight.class)) {
                    logger.warn("Failed to load class " + currentClass.getSimpleName() + ".");
                    logger.warn(currentClass.getSimpleName() + " constructor must have 3 parameters of definite types");
                    continue;
                }
                validClasses.add((Class<? extends Shape>) currentClass);
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return validClasses;
    }
}
