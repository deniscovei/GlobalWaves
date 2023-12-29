package data.entities.users;

import data.Database;
import data.entities.pages.Page;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils;
import utils.AppUtils.UserType;

import java.util.*;

import static utils.AppUtils.RES_COUNT_MAX;

/**
 * The type User.
 */
@Setter
@Getter
public abstract class User {
    protected String username = null;
    private int age = 0;
    private String city = null;
    private UserType userType = null;
    private Page currentPage = null;
    private boolean added = false;

    public interface Tops {
        Tops clone();

        default Map<String, Integer> sortMap(Map<String, Integer> unsortedMap) {
            List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortedMap.entrySet());

            list.sort(Comparator
                    .comparing(Map.Entry<String, Integer>::getValue).reversed()
                    .thenComparing(Map.Entry::getKey));

            LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
            int count = 0;

            for (Map.Entry<String, Integer> entry : list) {
                sortedMap.put(entry.getKey(), entry.getValue());

                if (++count == RES_COUNT_MAX) {
                    break;
                }
            }

            return sortedMap;
        }
    }

    protected Tops tops;

    /**
     * Instantiates a new User.
     */
    protected User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    protected User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
    }

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     * @param userType the user type
     */
    protected User(final String username, final int age, final String city,
                   final UserType userType) {
        this(username, age, city);
        this.userType = userType;
    }

    /**
     * returns true if the user is interacting with others and false otherwise
     *
     * @param timestamp the timestamp
     * @return true if the user is interacting with others and false otherwise
     */
    public abstract boolean interactingWithOthers(int timestamp);
}
