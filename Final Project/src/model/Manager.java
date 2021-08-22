package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static util.Flags.MANAGER_FILE;

public class Manager extends AbstractReadFile {

    private int id;
    private String name;
    private String passwd;

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    private void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public Manager readFromString(String s) {
        String[] profiles = s.split(SEPARATOR);
        Manager manager = new Manager();
        manager.setId(Integer.parseInt(profiles[0]));
        manager.setName(profiles[1]);
        manager.setPasswd(profiles[2]);
        return manager;
    }

    @Override
    public String toString() {
        return id + SEPARATOR + name + SEPARATOR + passwd;
    }

    private static List<Manager> listAllManager() {
        try {
            List<String> all = readFromFile(MANAGER_FILE);
            return all.stream().map(per -> new Manager().readFromString(per)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();

    }

    public static boolean isPasswdRight(String name, String passwd) {
        return listAllManager().stream().anyMatch(per -> name.equals(per.name) && passwd.equals(per.passwd));
    }

}