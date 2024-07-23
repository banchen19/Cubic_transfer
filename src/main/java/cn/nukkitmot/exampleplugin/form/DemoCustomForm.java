package cn.nukkitmot.exampleplugin.form;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.handler.FormResponseHandler;
import cn.nukkit.form.window.FormWindowCustom;

import java.util.Arrays;

public class DemoCustomForm {
    public static void open(Player player) {
        FormWindowCustom form = new FormWindowCustom("Title");
        // Add elements
        form.addElement(new ElementInput("Text Input", "Default Value"));
        form.addElement(new ElementDropdown("Dropdown", Arrays.asList("Option 1", "Option 2", "Option 3")));
        // Set submission action
        form.addHandler(FormResponseHandler.withoutPlayer(ignored -> {
            if (form.wasClosed()) return;
            String inputText = form.getResponse().getInputResponse(0); // Get the text input value
            int selectedIndex = form.getResponse().getDropdownResponse(1).getElementID(); // Get the dropdown selection index
            String selectedText = form.getResponse().getDropdownResponse(1).getElementContent(); // Get the dropdown selection text
            // Handle user-submitted data
        }));
        // Show the form to the player
        player.showFormWindow(form);
    }
}