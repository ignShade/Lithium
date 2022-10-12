package net.leonemc.lithium.utils.java;

import com.google.common.collect.ImmutableSet;
import net.leonemc.lithium.Lithium;
import org.bukkit.event.Listener;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {

    public void loadListenersFromPackage(String packageName) {
        for (Class<?> clazz : getClassesInPackage(packageName)) {
            if (!isListener(clazz)) continue;
            try {
                Lithium.getInstance().getServer().getPluginManager().registerEvents((Listener)clazz.newInstance(), Lithium.getInstance());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void loadCommandsFromPackage(String packageName) {
        for (Class<?> clazz : getClassesInPackage(packageName)) {
            try {
                clazz.newInstance();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public boolean isListener(Class<?> clazz) {
        for (Class<?> interfaze : clazz.getInterfaces()) {
            if (interfaze != Listener.class) continue;
            return true;
        }
        return false;
    }

    public Collection<Class<?>> getClassesInPackage(String packageName) {
        JarFile jarFile;
        ArrayList classes = new ArrayList();
        CodeSource codeSource = Lithium.getInstance().getClass().getProtectionDomain().getCodeSource();
        URL resource = codeSource.getLocation();
        String relPath = packageName.replace('.', '/');
        String resPath = resource.getPath().replace("%20", " ");
        String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
        try {
            jarFile = new JarFile(jarPath);
        }
        catch (IOException e) {
            throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
        }
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();
            String className = null;
            if (entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length() > relPath.length() + "/".length()) {
                className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
            }
            if (className == null) continue;
            Class<?> clazz = null;
            try {
                clazz = Class.forName(className);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (clazz == null) continue;
            classes.add(clazz);
        }
        try {
            jarFile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return ImmutableSet.copyOf(classes);
    }
}
