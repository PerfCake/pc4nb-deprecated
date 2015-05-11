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
package org.perfcake.pc4nb.ui.tableModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Scenario.Reporting.Reporter;

/**
 *
 * @author Andrej Halaj
 */
public class MessagesTableModel extends AbstractTableModel {
    private List<Message> messages =  new ArrayList<>();

    @Override
    public int getRowCount() {
        return messages .size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    public void addRow(Message message) {
        int lastRow = messages.size();
        insertRow(lastRow, message);
    }

    public void insertRow(int index, Message message) {
        messages.add(index, message);
        fireTableRowsInserted(index, index);
    }
    
    public void updateRow(int index, Message message) {
        messages.set(index, message);
        fireTableRowsUpdated(index, index);
    }

    public void removeRow(int rowNum) {
        messages.remove(rowNum);
        fireTableRowsDeleted(rowNum, rowNum);
    }
    
    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Message message = messages.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return message.getUri();
            case 1:
                return Integer.parseInt(message.getMultiplicity());
            case 2:
                return message.getValidatorRef();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Message URI";
            case 1:
                return "Multiplicity";
            case 2:
                return "Attached validators";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
}