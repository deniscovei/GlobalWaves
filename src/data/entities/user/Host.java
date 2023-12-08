package data.entities.user;

import utils.Constants.UserType;
import utils.Constants.Page;

public class Host extends User {
    public Host(String username, int age, String city) {
        super(username, age, city);
        setUserType(UserType.HOST);
        setCurrentPage(Page.HOST_PAGE);
    }
}
