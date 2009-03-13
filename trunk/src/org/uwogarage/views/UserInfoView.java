package org.uwogarage.views;

import javax.swing.JPanel;

import org.uwogarage.models.UserModel;

public class UserInfoView extends TabView {
    static public JPanel view(UserModel user) {
        return grid(
            grid.row(
                grid.cell(2, label("User Info"))
                    .margin(10, 10, 10, 10)
            ),
            grid.row(
                grid.cell(label("Full Name:"))
                    .margin(0, 10, 10, 10)
                    .anchor(0, 1, 0, 0),
                grid.cell(label(user.getFirstName() +" "+ user.getLastName()))
                    .margin(0, 10, 10, 0)
                    .anchor(0, 0, 0, 1)
            ),
            grid.row(
                grid.cell(label("Phone Number:"))
                    .margin(0, 10, 10, 10)
                    .anchor(0, 1, 0, 0),
                grid.cell(label(user.getPhoneNumber()))
                    .margin(0, 10, 10, 0)
                    .anchor(0, 0, 0, 1)
            ),
            grid.row(
                grid.cell(label("Average Sale Rating:"))
                    .margin(0, 10, 10, 10)
                    .anchor(0, 1, 0, 0),
                grid.cell(label(String.valueOf(user.getRating())))
                    .margin(0, 10, 10, 0)
                    .anchor(0, 0, 0, 1)
            )
        );
    }
}
