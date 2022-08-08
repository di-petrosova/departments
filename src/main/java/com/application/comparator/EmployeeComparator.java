package com.application.comparator;

import com.application.model.EmployeeModel;

import java.util.Comparator;
import java.util.Date;

public class EmployeeComparator implements Comparator<EmployeeModel> {
    @Override
    public int compare(EmployeeModel o1, EmployeeModel o2) {
        boolean experience1 = o1.isExperience();
        boolean experience2 = o2.isExperience();

        int firstComparison = Boolean.compare(experience1, experience2);

        if (firstComparison != 0) {
            return firstComparison;
        }

        String firstName1 = o1.getFirstName();
        String firstName2 = o2.getFirstName();
        Date dateBirth1 = o1.getCreatedDate();
        Date dateBirth2 = o2.getCreatedDate();

        if (experience1) {
            if (firstName1 == null && firstName2 == null) {
                return 0;
            }
            if (firstName1 == null) {
                return 1;
            }
            if (firstName2 == null) {
                return -1;
            }
            return firstName1.compareTo(firstName2);
        } else {
            if (dateBirth1 == null && dateBirth2 == null) {
                return 0;
            }
            if (dateBirth1 == null) {
                return 1;
            }
            if (dateBirth2 == null) {
                return -1;
            }
            return dateBirth1.compareTo(dateBirth2);
        }
    }

}
