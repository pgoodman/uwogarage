package org.uwogarage.views;

import javax.swing.JPanel;
import javax.swing.JSlider;

import org.uwogarage.models.UserModel;

public class UserInfoView extends TabView {
    static public JPanel view(UserModel user) {
        
        
        
        String[] user_phone = user.getPhoneNumber();
        JSlider rating = Slider.view(0, 4, (int)user.getRating(), null);
        rating.setEnabled(false);
        
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
                grid.cell(label(String.format("(%s) %s-%s", user_phone[0], user_phone[1], user_phone[2])))
                    .margin(0, 10, 10, 0)
                    .anchor(0, 0, 0, 1)
            ),
            grid.row(
                grid.cell(label("Average Sale Rating:"))
                    .margin(0, 10, 10, 10)
                    .anchor(0, 1, 0, 0),
                grid.cell(rating)
                    .margin(0, 10, 10, 0)
                    .anchor(0, 0, 0, 1)
            )
        );
    }
}
