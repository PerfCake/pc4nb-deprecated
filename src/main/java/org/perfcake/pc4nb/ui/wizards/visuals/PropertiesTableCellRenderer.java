/*
 * Copyright 2015 Andrej Halaj.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.perfcake.pc4nb.ui.wizards.visuals;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.perfcake.model.Property;
import org.perfcake.pc4nb.ui.tableModel.PropertiesTableModel;

/**
 *
 * @author Andrej Halaj
 */
public class PropertiesTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        PropertiesTableModel model = (PropertiesTableModel) table.getModel();
        List<Property> defaultValues = model.getDefaultValues();
        String defaultValue = defaultValues.get(row).getValue();
        String currentValue = (String) value;

        if (column == 0) {
            if (defaultValue.equals("null") || defaultValue.isEmpty()) {
                cell.setForeground(Color.RED);
            } else {
                cell.setForeground(Color.BLACK);
            }
        } else if (column == 1) {
            if (defaultValue.equals("null") || defaultValue.isEmpty()) {
                if (defaultValue.equals(currentValue)) {
                    cell.setForeground(Color.RED);
                } else {
                    cell.setForeground(Color.GREEN);
                }
            } else {
                if (defaultValue.equals(currentValue)) {
                    cell.setForeground(Color.GRAY);
                } else {
                    cell.setForeground(Color.GREEN);
                }
            }
        } else {
            cell.setForeground(Color.BLUE);
        }
        
        return cell;
    }

}